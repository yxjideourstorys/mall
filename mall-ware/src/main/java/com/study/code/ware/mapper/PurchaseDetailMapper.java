package com.study.code.ware.mapper;

import com.study.code.ware.entity.PurchaseDetailEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author suiweidong
 * @email 7334501@qq.com
 * @date 2021-05-06 04:53:45
 */
@Mapper
public interface PurchaseDetailMapper extends BaseMapper<PurchaseDetailEntity> {

    void updateBatchByPurchaseId(@Param("entities") List<PurchaseDetailEntity> entities);

}
