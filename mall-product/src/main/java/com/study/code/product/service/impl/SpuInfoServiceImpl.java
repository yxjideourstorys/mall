package com.study.code.product.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.study.code.commons.feign.coupon.CouponFeignService;
import com.study.code.commons.to.product.SkuReductionTO;
import com.study.code.commons.to.product.SpuBoundsTO;
import com.study.code.commons.vo.product.*;
import com.study.code.product.entity.*;
import com.study.code.product.service.*;
import com.study.code.utils.util.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.code.commons.util.PageUtils;
import com.study.code.commons.util.Query;

import com.study.code.product.mapper.SpuInfoMapper;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoMapper, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @Autowired
    private SpuImagesService spuImagesService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private SkuImagesService skuImagesService;

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    private CouponFeignService couponFeignService;

    @Autowired
    private AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<SpuInfoEntity> queryWrapper = new QueryWrapper<>();
        String key = MapUtil.getStr(params, "key");
        if (StringUtils.isNotEmpty(key)) {
            queryWrapper.and(wrapper -> {
                wrapper.eq("id", key)
                        .or().like("spu_name", key)
                        .or().like("spu_description", key);
            });
        }

        String status = MapUtil.getStr(params, "status");
        if (StringUtils.isNotEmpty(status)) {
            queryWrapper.eq("publish_status", status);
        }

        String catelogId = MapUtil.getStr(params, "catelogId");
        if (StringUtils.isNotEmpty(catelogId)) {
            queryWrapper.eq("catalog_id", catelogId);
        }

        String brandId = MapUtil.getStr(params, "brandId");
        if (StringUtils.isNotEmpty(brandId)) {
            queryWrapper.eq("brand_id", brandId);
        }


        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void spuInfoSave(SpuSaveVO spuSaveVO) {
        // #1 保存spu基本信息 pms_spu_info
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(spuSaveVO, spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.save(spuInfoEntity);

        // #2 保存spu信息介绍 pms_spu_info_desc
        SpuInfoDescEntity spuDesc = new SpuInfoDescEntity();
        spuDesc.setSpuId(spuInfoEntity.getId());
        spuDesc.setDecript(String.join(",", spuSaveVO.getDecript()));
        this.spuInfoDescService.saveSpuDesc(spuDesc);

        // #3 保存spu图片 pms_spu_images
        this.spuImagesService.saveSpuImages(spuInfoEntity.getId(), spuSaveVO.getImages());

        // #4 保存spu属性值 pms_product_attr_value
        List<BaseAttrsVO> baseAttrs = spuSaveVO.getBaseAttrs();
        List<ProductAttrValueEntity> productAttrValueEntityList = baseAttrs.stream().map(item -> {
            ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();
            productAttrValueEntity.setSpuId(spuInfoEntity.getId());
            productAttrValueEntity.setAttrId(item.getAttrId());
            productAttrValueEntity.setQuickShow(item.getShowDesc());
            productAttrValueEntity.setAttrValue(item.getAttrValues());

            AttrEntity attrEntity = attrService.getById(item.getAttrId());
            if (ObjectUtil.isNotEmpty(attrEntity)) {
                productAttrValueEntity.setAttrName(attrEntity.getAttrName());
            }

            return productAttrValueEntity;
        }).collect(Collectors.toList());
        this.productAttrValueService.saveProductAttrValue(productAttrValueEntityList);

        // #5 保存积分 远程调用 sms_spu_bounds
        SpuBoundsTO spuBoundsTO = new SpuBoundsTO();
        BeanUtils.copyProperties(spuSaveVO.getBounds(), spuBoundsTO);
        spuBoundsTO.setSpuId(spuInfoEntity.getId());
        R boundsResult = this.couponFeignService.saveSpuBounds(spuBoundsTO);
        if (boundsResult.getCode() != 0) {
            log.info("远程调用保存spuBounds失败...");
        }

        // #6 保存sku基本信息
        List<SkusVO> skusVOList = spuSaveVO.getSkus();
        if (ObjectUtil.isNotEmpty(skusVOList)) {
            skusVOList.forEach(skusVO -> {
                // 默认图片
                List<ImagesVO> skuImagesVOList = skusVO.getImages();
                String skuDefaultImg = "";
                for (ImagesVO skuImage : skuImagesVOList) {
                    if (skuImage.getDefaultImg() == 1) {
                        skuDefaultImg = skuImage.getImgUrl();
                        break;
                    }
                }

                // #6.1 保存sku信息 pms_sku_info
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(skusVO, skuInfoEntity);
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(skuDefaultImg);
                skuInfoEntity.setSaleCount(0L);
                this.skuInfoService.saveSkuInfo(skuInfoEntity);

                // #6.2 保存sku图片信息  pms_sku_images
                List<SkuImagesEntity> skuImagesEntityList = skuImagesVOList.stream()
                        .filter(skuImages -> StringUtils.isNotEmpty(skuImages.getImgUrl()))
                        .map(skuImages -> {
                            SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                            BeanUtils.copyProperties(skuImages, skuImagesEntity);
                            skuImagesEntity.setSkuId(skuInfoEntity.getSkuId());
                            return skuImagesEntity;
                        }).collect(Collectors.toList());
                this.skuImagesService.saveBatch(skuImagesEntityList);

                // #6.3 保存sku销售属性&值  pms_sku_sale_attr_value
                List<SkuSaleAttrValueEntity> skuSaleAttrValueEntityList = skusVO.getAttr().stream().map(saleAttrVO -> {
                    SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(saleAttrVO, skuSaleAttrValueEntity);
                    skuSaleAttrValueEntity.setSkuId(skuInfoEntity.getSkuId());
                    return skuSaleAttrValueEntity;
                }).collect(Collectors.toList());
                this.skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntityList);

                // #6.4 保存优惠、满减等信息  远程调用
                // #6.4.1 阶梯 sms_sku_ladder
                // #6.4.1 满减 sms_sku_full_reduction
                // #6.4.1 会员价格 sms_member_price
                if (skusVO.getFullCount() > 0 || skusVO.getFullPrice().compareTo(new BigDecimal("0")) > 0) {
                    SkuReductionTO skuReductionTO = new SkuReductionTO();
                    BeanUtils.copyProperties(skusVO, skuReductionTO);
                    skuReductionTO.setSkuId(skuInfoEntity.getSkuId());
                    R skuReductionResult = this.couponFeignService.saveSkuReduction(skuReductionTO);
                    if (skuReductionResult.getCode() != 0) {
                        log.error("远程保存sku优惠信息失败");
                    }
                }
            });
        }
    }
}