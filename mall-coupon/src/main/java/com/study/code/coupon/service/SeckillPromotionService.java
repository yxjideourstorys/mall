package com.study.code.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.code.common.utils.PageUtils;
import com.study.code.coupon.entity.SeckillPromotionEntity;

import java.util.Map;

/**
 * 秒杀活动
 *
 * @author suiweidong
 * @email 7334501@qq.com
 * @date 2021-05-06 04:42:24
 */
public interface SeckillPromotionService extends IService<SeckillPromotionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

