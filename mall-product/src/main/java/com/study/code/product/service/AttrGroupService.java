package com.study.code.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.code.commons.util.PageUtils;
import com.study.code.product.entity.AttrEntity;
import com.study.code.product.entity.AttrGroupEntity;
import com.study.code.product.vo.AttrGroupReqVO;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author suiweidong
 * @email 7334501@qq.com
 * @date 2021-05-06 03:13:45
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByCatId(Map<String, Object> params, Long catId);

    PageUtils queryNoattrRelationList(Map<String, Object> params, Long attrGroupId);

    void saveAttrRelation(AttrGroupReqVO[] attrGroupReqVO);

    List<AttrEntity> queryAttrRelationList(Long attrGroupId);

    void attrRelationDelete(List<AttrGroupReqVO> asList);
}

