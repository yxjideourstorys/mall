package com.study.code.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.code.commons.util.PageUtils;
import com.study.code.product.entity.AttrEntity;
import com.study.code.commons.vo.product.AttrReqVO;
import com.study.code.commons.vo.product.AttrResVO;

import java.util.Map;

/**
 * 商品属性
 *
 * @author suiweidong
 * @email 7334501@qq.com
 * @date 2021-05-06 03:13:45
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrReqVO attr);

    PageUtils queryAttrList(Map<String, Object> params, Long catelogId, String type);

    AttrResVO queryAttrInfo(Long attrId);

    void updateAttr(AttrReqVO attrVO);
}

