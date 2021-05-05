package com.study.code.product.mapper;

import com.study.code.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author suiweidong
 * @email 7334501@qq.com
 * @date 2021-05-06 03:13:45
 */
@Mapper
public interface CategoryMapper extends BaseMapper<CategoryEntity> {
	
}
