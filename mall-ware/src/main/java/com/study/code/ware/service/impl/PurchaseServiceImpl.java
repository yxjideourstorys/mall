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

import com.study.code.ware.mapper.PurchaseMapper;
import com.study.code.ware.entity.PurchaseEntity;
import com.study.code.ware.service.PurchaseService;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, PurchaseEntity> implements PurchaseService {

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

}