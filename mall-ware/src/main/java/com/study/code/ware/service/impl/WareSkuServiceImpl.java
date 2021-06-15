package com.study.code.ware.service.impl;

import cn.hutool.core.map.MapUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.code.commons.util.PageUtils;
import com.study.code.commons.util.Query;

import com.study.code.ware.mapper.WareSkuMapper;
import com.study.code.ware.entity.WareSkuEntity;
import com.study.code.ware.service.WareSkuService;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuMapper, WareSkuEntity> implements WareSkuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<WareSkuEntity> queryWrapper = new QueryWrapper<>();
        String key = MapUtil.getStr(params, "key");
        if (StringUtils.isNotEmpty(key)) {
            queryWrapper.and(wrapper ->
                    wrapper.eq("id", key)
                            .or().eq("sku_id", key)
                            .or().eq("ware_id", key)
                            .or().like("sku_name", key));
        }

        String wareId = MapUtil.getStr(params, "wareId");
        if (StringUtils.isNotEmpty(wareId)) {
            queryWrapper.eq("ware_id", wareId);
        }

        String skuId = MapUtil.getStr(params, "skuId");
        if (StringUtils.isNotEmpty(skuId)) {
            queryWrapper.eq("sku_id", skuId);
        }

        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

}