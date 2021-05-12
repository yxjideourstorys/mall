package com.study.code.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.code.commons.util.PageUtils;
import com.study.code.commons.util.Query;

import com.study.code.product.mapper.CategoryMapper;
import com.study.code.product.entity.CategoryEntity;
import com.study.code.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> queryCategoryTree() {
        // 查询出所有的商品分类列表
        List<CategoryEntity> allCategorys = this.baseMapper.selectList(null);

        // 组装成父子的树型结构
        List<CategoryEntity> categoryList = allCategorys.stream().filter(currCategory->
            currCategory.getParentCid() == 0
        ).map(category->{
            // 获取每一个菜单的子分类
            category.setChildrens(getChildrens(allCategorys, category));
            return category;
        }).sorted((sort1, sort2)->
            (sort1.getSort()==null?0:sort1.getSort()) - (sort2.getSort()==null?0:sort2.getSort())
        ).collect(Collectors.toList());

        return categoryList;
    }

    private List<CategoryEntity> getChildrens(List<CategoryEntity> allCategorys, CategoryEntity curr){

        List<CategoryEntity> childrenList = allCategorys.stream().filter(all->
                // 查询列表里面父分类id 为 当前分类的id的数据
            all.getParentCid() == curr.getCatId()
        ).map(category->{
            // 子分类下面还有子分类
            category.setChildrens(getChildrens(allCategorys, category));
            return category;
        }).sorted((sort1, sort2)->
            (sort1.getSort()==null?0:sort1.getSort()) - (sort2.getSort()==null?0:sort2.getSort())
        ).collect(Collectors.toList());

        return childrenList;
    }
}