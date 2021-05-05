package com.study.code.order.mapper;

import com.study.code.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author suiweidong
 * @email 7334501@qq.com
 * @date 2021-05-06 02:12:14
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderEntity> {
	
}
