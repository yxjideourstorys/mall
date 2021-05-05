package com.study.code.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.code.common.utils.PageUtils;
import com.study.code.coupon.entity.CouponHistoryEntity;

import java.util.Map;

/**
 * 优惠券领取历史记录
 *
 * @author suiweidong
 * @email 7334501@qq.com
 * @date 2021-05-06 04:42:25
 */
public interface CouponHistoryService extends IService<CouponHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

