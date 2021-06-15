package com.study.code.product.service.impl;

import cn.hutool.core.map.MapUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.code.commons.util.PageUtils;
import com.study.code.commons.util.Query;

import com.study.code.product.mapper.SkuInfoMapper;
import com.study.code.product.entity.SkuInfoEntity;
import com.study.code.product.service.SkuInfoService;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfoEntity> implements SkuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<SkuInfoEntity> queryWrapper = new QueryWrapper<>();

        String key = MapUtil.getStr(params, "key");
        if (StringUtils.isNotEmpty(key)) {
            queryWrapper.and(wrapper -> {
                wrapper.eq("sku_id", key)
                        .or().eq("spu_id", key)
                        .or().eq("catalog_id", key)
                        .or().eq("brand_id", key)
                        .or().like("sku_name", key)
                        .or().like("sku_title", key)
                        .or().like("sku_subtitle", key);
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

        String min = MapUtil.getStr(params, "min");
        if (StringUtils.isNotEmpty(min)) {
            queryWrapper.ge("price", min);
        }

        String max = MapUtil.getStr(params, "max");
        if (StringUtils.isNotEmpty(max)) {
            queryWrapper.le("price", max);
        }

        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
        this.save(skuInfoEntity);
    }

}