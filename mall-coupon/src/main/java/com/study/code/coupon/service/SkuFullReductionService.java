package com.study.code.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.code.commons.to.product.SkuReductionTO;
import com.study.code.commons.util.PageUtils;
import com.study.code.coupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author suiweidong
 * @email 7334501@qq.com
 * @date 2021-05-06 04:42:23
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuReduction(SkuReductionTO skuReductionTO);

    void saveSkuFullReduction(SkuFullReductionEntity skuFullReductionEntity);

}

