package com.study.code.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.code.commons.util.PageUtils;
import com.study.code.product.entity.AttrAttrgroupRelationEntity;

import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author suiweidong
 * @email 7334501@qq.com
 * @date 2021-05-06 03:13:45
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

