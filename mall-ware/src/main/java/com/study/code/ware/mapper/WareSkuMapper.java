package com.study.code.ware.mapper;

import com.study.code.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品库存
 * 
 * @author suiweidong
 * @email 7334501@qq.com
 * @date 2021-05-06 04:53:45
 */
@Mapper
public interface WareSkuMapper extends BaseMapper<WareSkuEntity> {

    void saveBatchWareSku(@Param("entities") List<WareSkuEntity> wareSkuSaveList);
}
