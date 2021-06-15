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

import com.study.code.ware.mapper.PurchaseDetailMapper;
import com.study.code.ware.entity.PurchaseDetailEntity;
import com.study.code.ware.service.PurchaseDetailService;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailMapper, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<PurchaseDetailEntity> queryWrapper = new QueryWrapper<>();
        String key = MapUtil.getStr(params, "key");
        if (StringUtils.isNotEmpty(key)) {
            queryWrapper.and(wrapper ->
                    wrapper.eq("id", key)
                            .or().eq("purchase_id", key)
                            .or().eq("sku_id", key)
                            .or().eq("ware_id", key));
        }

        String status = MapUtil.getStr(params, "status");
        if (StringUtils.isNotEmpty(status)) {
            queryWrapper.eq("status", status);
        }

        String wareId = MapUtil.getStr(params, "wareId");
        if (StringUtils.isNotEmpty(wareId)) {
            queryWrapper.eq("ware_id", wareId);
        }

        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

}