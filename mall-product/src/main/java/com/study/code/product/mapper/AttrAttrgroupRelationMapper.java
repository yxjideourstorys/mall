package com.study.code.product.mapper;

import com.study.code.product.entity.AttrAttrgroupRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.code.product.vo.AttrGroupReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性&属性分组关联
 * 
 * @author suiweidong
 * @email 7334501@qq.com
 * @date 2021-05-06 03:13:45
 */
@Mapper
public interface AttrAttrgroupRelationMapper extends BaseMapper<AttrAttrgroupRelationEntity> {

    /**
     * 除了map和pojo类型，都要使用@param
     *
     * @param relation 关联实体
     */
    void updateAttrgroupRelation(AttrAttrgroupRelationEntity relation);

    void updateBatchByAttrId(@Param("entities") List<AttrAttrgroupRelationEntity> entities);

    void attrRelationDelete(@Param("entities") List<AttrAttrgroupRelationEntity> entities);
}
