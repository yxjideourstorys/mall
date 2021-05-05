package com.study.code.coupon.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.code.common.utils.PageUtils;
import com.study.code.common.utils.Query;

import com.study.code.coupon.mapper.HomeAdvMapper;
import com.study.code.coupon.entity.HomeAdvEntity;
import com.study.code.coupon.service.HomeAdvService;


@Service("homeAdvService")
public class HomeAdvServiceImpl extends ServiceImpl<HomeAdvMapper, HomeAdvEntity> implements HomeAdvService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HomeAdvEntity> page = this.page(
                new Query<HomeAdvEntity>().getPage(params),
                new QueryWrapper<HomeAdvEntity>()
        );

        return new PageUtils(page);
    }

}