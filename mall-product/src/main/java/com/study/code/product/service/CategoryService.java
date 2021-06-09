package com.study.code.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.code.commons.util.PageUtils;
import com.study.code.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author suiweidong
 * @email 7334501@qq.com
 * @date 2021-05-06 03:13:45
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询所有商品的分类及子类，以树形结构展示
     * @return List<CategoryEntity>
     */
    List<CategoryEntity> queryCategoryTree();

    /**
     * 根据catIds 批量删除菜单
     * @param catIds
     */
    void removeMenusByCatIds(List<Long> catIds);

    /**
     * 根据catelogId 获取分类路径 父/子/孙
     * @param catelogId
     * @return
     */
    Long[] getCategoryPath(Long catelogId);

    /**
     * 修改分类及分类相关数据
     * @param category
     */
    void updateDetail(CategoryEntity category);
}

