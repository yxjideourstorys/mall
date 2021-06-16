package com.study.code.ware.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.code.commons.constant.WareConstant;
import com.study.code.commons.exception.BizException;
import com.study.code.commons.feign.product.ProductFeignService;
import com.study.code.commons.util.PageUtils;
import com.study.code.commons.util.Query;
import com.study.code.commons.vo.ware.ItemVO;
import com.study.code.commons.vo.ware.MergePurchaseVO;
import com.study.code.commons.vo.ware.PurchaseDoneVO;
import com.study.code.utils.util.R;
import com.study.code.ware.entity.PurchaseDetailEntity;
import com.study.code.ware.entity.PurchaseEntity;
import com.study.code.ware.entity.WareSkuEntity;
import com.study.code.ware.mapper.PurchaseMapper;
import com.study.code.ware.service.PurchaseDetailService;
import com.study.code.ware.service.PurchaseService;
import com.study.code.ware.service.WareSkuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, PurchaseEntity> implements PurchaseService {

    @Autowired
    private PurchaseDetailService purchaseDetailService;

    @Autowired
    private WareSkuService wareSkuService;

    @Autowired
    private ProductFeignService productFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<PurchaseEntity> queryWrapper = new QueryWrapper<>();
        String key = MapUtil.getStr(params, "key");
        if (StringUtils.isNotEmpty(key)) {
            queryWrapper.and(wrapper ->
                    wrapper.eq("id", key)
                            .or().eq("assignee_id", key)
                            .or().eq("phone", key)
                            .or().eq("ware_id", key)
                            .or().like("assignee_name", key));
        }

        String status = MapUtil.getStr(params, "status");
        if (StringUtils.isNotEmpty(status)) {
            queryWrapper.eq("status", status);
        }

        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<PurchaseEntity> queryUnreceiveList(Map<String, Object> params) {
        QueryWrapper<PurchaseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", WareConstant.PurchaseEnum.CREATED.getCode())
                .or().eq("status", WareConstant.PurchaseEnum.ASSIGNED.getCode());
        return this.baseMapper.selectList(queryWrapper);
    }

    @Transactional
    @Override
    public void merge(MergePurchaseVO mergePurchase) {
        Long purchaseId = mergePurchase.getPurchaseId();
        if (ObjectUtil.isEmpty(purchaseId)){
            // 新建采购单
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setPriority(0);
            purchaseEntity.setStatus(WareConstant.PurchaseEnum.CREATED.getCode());
            purchaseEntity.setCreateTime(new Date());
            purchaseEntity.setUpdateTime(new Date());
            this.baseMapper.insert(purchaseEntity);
            purchaseId = purchaseEntity.getId();
        }else {
            // 判断该采购单，状态是否为新建或已分配
            PurchaseEntity purchase = this.getById(purchaseId);
            if (ObjectUtil.isEmpty(purchase)){
               throw new BizException("该【" + purchaseId +"】采购单不存在");
            }
            if (purchase.getStatus() != WareConstant.PurchaseEnum.CREATED.getCode() || purchase.getStatus() != WareConstant.PurchaseEnum.ASSIGNED.getCode()){
                throw new BizException("该【" + purchaseId +"】采购单状态已不可合并");
            }
        }
        // 更新采购需求
        List<Long> items = mergePurchase.getItems();
        final Long finalPurchaseId = purchaseId;
        List<PurchaseDetailEntity> purchaseDetailEntityList = items.stream().map(purchaseDetailId -> {
            PurchaseDetailEntity purchaseDetail = this.purchaseDetailService.getById(purchaseDetailId);
            purchaseDetail.setPurchaseId(finalPurchaseId);
            purchaseDetail.setStatus(WareConstant.PurchaseDetailEnum.ASSIGNED.getCode());
            return purchaseDetail;
        }).collect(Collectors.toList());
        this.purchaseDetailService.updateBatchById(purchaseDetailEntityList);

        // 更新采购单
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(purchaseId);
        purchaseEntity.setWareId(mergePurchase.getWareId());
        purchaseEntity.setUpdateTime(new Date());
        this.baseMapper.updateById(purchaseEntity);
    }

    @Override
    @Transactional
    public void received(List<Long> purchases) {
        log.info("received--->param：" + purchases);

        List<PurchaseEntity> purchaseList = new ArrayList<>();
        List<PurchaseDetailEntity> purchaseDetailList = new ArrayList<>();
        purchases.forEach(purchaseId -> {
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setId(purchaseId);
            purchaseEntity.setStatus(WareConstant.PurchaseEnum.RECEIVED.getCode());
            purchaseEntity.setUpdateTime(new Date());
            purchaseList.add(purchaseEntity);

            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            purchaseDetailEntity.setPurchaseId(purchaseId);
            purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailEnum.PURCHASE_ING.getCode());
            purchaseDetailList.add(purchaseDetailEntity);
        });

        // 更新采购单状态
        log.info("received-updateBatchById--->param：" + purchaseList);
        this.updateBatchById(purchaseList);

        // 更新采购需求状态
        log.info("received-purchaseDetailService.updateBatchByPurchaseId--->param：" + purchaseDetailList);
        this.purchaseDetailService.updateBatchByPurchaseId(purchaseDetailList);

    }

    @Override
    @Transactional
    public void done(PurchaseDoneVO purchaseDone) {

        // 更新需求单
        boolean flag = true;
        List<PurchaseDetailEntity> purchaseDetailList = new ArrayList<>();
        List<WareSkuEntity> wareSkuSaveList = new ArrayList<>();
        List<WareSkuEntity> wareSkuUpdateList = new ArrayList<>();
        for (ItemVO item : purchaseDone.getItems()) {
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            if (item.getStatus() == WareConstant.PurchaseDetailEnum.PURCHASE_ERROR.getCode()){
                flag = false;
                purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailEnum.PURCHASE_ERROR.getCode());
            }else {
                purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailEnum.DONE.getCode());
            }
            purchaseDetailEntity.setId(item.getItemId());
            purchaseDetailList.add(purchaseDetailEntity);

            // 无异常数据入库存
            if (flag){
                PurchaseDetailEntity detailEntity = this.purchaseDetailService.getById(item.getItemId());
                Long skuId = detailEntity.getSkuId();
                Long wareId = detailEntity.getWareId();
                Integer skuNum = detailEntity.getSkuNum();

                WareSkuEntity wareSkuEntity = this.wareSkuService.getOne(new QueryWrapper<WareSkuEntity>().eq("sku_id", skuId).eq("ware_id", wareId));
                if (ObjectUtil.isNotEmpty(wareSkuEntity)){
                    // 以前存在库存
                    wareSkuEntity.setStock(skuNum + wareSkuEntity.getStock());
                    wareSkuUpdateList.add(wareSkuEntity);
                }else {
                    // 以前无库存
                    WareSkuEntity wareSkuSave = new WareSkuEntity();
                    wareSkuSave.setSkuId(skuId);
                    wareSkuSave.setWareId(wareId);
                    wareSkuSave.setStockLocked(0);
                    wareSkuSave.setStock(skuNum);
                    try {
                        R skuInfoFeign = this.productFeignService.skuInfo(skuId);
                        if (skuInfoFeign.getCode() == 0){
                            Map<String, Object> skuInfo = (Map<String, Object>) skuInfoFeign.get("skuInfo");
                            wareSkuSave.setSkuName(MapUtil.getStr(skuInfo, "skuName"));
                        }
                    }catch (Exception e){
                        log.error("异常不回滚" + e);
                    }
                    wareSkuSaveList.add(wareSkuSave);
                }
            }
        }
        // 批量更新需求单状态
        this.purchaseDetailService.updateBatchById(purchaseDetailList);
        // 批量保存库存

        if (ObjectUtil.isNotEmpty(wareSkuSaveList)){
            wareSkuService.saveBatchWareSku(wareSkuSaveList);
        }

        // 批量更新库存
        if (ObjectUtil.isNotEmpty(wareSkuUpdateList)){
            wareSkuService.updateBatchById(wareSkuUpdateList);
        }

        // 更新采购单状态
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(purchaseDone.getId());
        purchaseEntity.setStatus(flag?WareConstant.PurchaseEnum.DONE.getCode():WareConstant.PurchaseEnum.HAS_ERROR.getCode());
        purchaseEntity.setUpdateTime(new Date());
        this.updateById(purchaseEntity);
    }
}