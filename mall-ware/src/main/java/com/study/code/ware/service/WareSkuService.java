package com.study.code.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.code.common.utils.PageUtils;
import com.study.code.ware.entity.WareSkuEntity;

import java.util.Map;

/**
 * 商品库存
 *
 * @author suiweidong
 * @email 7334501@qq.com
 * @date 2021-05-06 04:53:45
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

