package com.study.code.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.code.common.utils.PageUtils;
import com.study.code.coupon.entity.SkuLadderEntity;

import java.util.Map;

/**
 * 商品阶梯价格
 *
 * @author suiweidong
 * @email 7334501@qq.com
 * @date 2021-05-06 04:42:24
 */
public interface SkuLadderService extends IService<SkuLadderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

