package com.study.code.ware.controller;

import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.util.ObjectUtil;
import com.study.code.commons.constant.WareConstant;
import com.study.code.commons.vo.ware.MergePurchaseVO;
import com.study.code.commons.vo.ware.PurchaseDoneVO;
import com.study.code.ware.entity.PurchaseDetailEntity;
import com.study.code.ware.service.PurchaseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.study.code.ware.entity.PurchaseEntity;
import com.study.code.ware.service.PurchaseService;
import com.study.code.commons.util.PageUtils;
import com.study.code.utils.util.R;



/**
 * 采购信息
 *
 * @author suiweidong
 * @email 7334501@qq.com
 * @date 2021-05-06 04:53:45
 */
@RestController
@RequestMapping("ware/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private PurchaseDetailService purchaseDetailService;

    /**
     * 获取未接收采购单列表
     */
    @GetMapping("/unreceive/list")
    public R unreceiveList(@RequestParam Map<String, Object> params){
        List<PurchaseEntity> result = purchaseService.queryUnreceiveList(params);

        return R.ok().put("list", result);
    }

    /**
     * 合并整单
     */
    @PostMapping("/merge")
    public R merge(@RequestBody MergePurchaseVO mergePurchase){
        purchaseService.merge(mergePurchase);

        return R.ok();
    }

    /**
     * 领取采购单
     */
    @PostMapping("/received")
    public R received(@RequestBody Long[] purchaseIds){

        List<Long> purchaselist = Arrays.asList(purchaseIds);
        for (Long purchaseId : purchaselist) {
            PurchaseEntity purchase = this.purchaseService.getById(purchaseId);
            if (ObjectUtil.isEmpty(purchase)) {
                return R.error("该【" + purchaseId +"】采购单不存在");
            }
            if (WareConstant.PurchaseEnum.CREATED.getCode() == purchase.getStatus()){
                return R.error("请为该【" + purchaseId +"】采购单分配采购人员");
            }else if (WareConstant.PurchaseEnum.ASSIGNED.getCode() != purchase.getStatus()){
                return R.error("该【" + purchaseId +"】采购单状态不可进行领取");
            }
            List<PurchaseDetailEntity> purchaseDetail = this.purchaseDetailService.getPurchaseDetail(purchaseId);
            if (ObjectUtil.isEmpty(purchaseDetail)) {
                return R.error("该【" + purchaseId +"】采购单中采购需求不存在");
            }
        }

        purchaseService.received(purchaselist);

        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/done")
    public R done(@RequestBody PurchaseDoneVO purchaseDone){
        purchaseService.done(purchaseDone);

        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		PurchaseEntity purchase = purchaseService.getById(id);

        return R.ok().put("purchase", purchase);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PurchaseEntity purchase){
        purchase.setCreateTime(new Date());
        purchase.setUpdateTime(new Date());
		purchaseService.save(purchase);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PurchaseEntity purchase){
		purchaseService.updateById(purchase);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		purchaseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
