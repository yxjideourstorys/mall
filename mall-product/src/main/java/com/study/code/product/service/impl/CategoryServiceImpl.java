package com.study.code.product.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.code.commons.exception.BizException;
import com.study.code.commons.util.PageUtils;
import com.study.code.commons.util.Query;
import com.study.code.product.entity.CategoryBrandRelationEntity;
import com.study.code.product.entity.CategoryEntity;
import com.study.code.product.mapper.CategoryMapper;
import com.study.code.product.service.CategoryBrandRelationService;
import com.study.code.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

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
            category.setChildren(getChildrens(allCategorys, category));
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
            category.setChildren(getChildrens(allCategorys, category));
            return category;
        }).sorted((sort1, sort2)->
            (sort1.getSort()==null?0:sort1.getSort()) - (sort2.getSort()==null?0:sort2.getSort())
        ).collect(Collectors.toList());

        return childrenList;
    }

    @Override
    public void removeMenusByCatIds(List<Long> catIds) {

        // 1、检查当前删除的菜单，是否被别的地方引用

        // 逻辑删除 mybatis-plus 可以统一全局配置
        this.baseMapper.deleteBatchIds(catIds);
    }

    @Override
    public Long[] getCategoryPath(Long catelogId) {

        // 根据当前分类信息查询父分类信息
        List<Long> paths = new ArrayList<>();
        List<Long> categoryPath = findPath(catelogId, paths);

        // 反转list   孙/子/父    ->   父/子/孙
        Collections.reverse(categoryPath);
        return categoryPath.toArray(new Long[categoryPath.size()]);
    }

    private List<Long> findPath(Long catelogId, List<Long> paths) {
        // 根据当前分类id查询父分类信息
        paths.add(catelogId);
        CategoryEntity category = getById(catelogId);
        if (category.getParentCid() != 0){
            findPath(category.getParentCid(), paths);
        }
        return paths;
    }

    @Override
    @Transactional
    public void updateDetail(CategoryEntity category) {
        CategoryEntity caty = getById(category.getCatId());
        if (ObjectUtil.isEmpty(caty)){
            throw new BizException("该id【"+ category.getCatId() +"】的分类信息不存在");
        }

        // 修改分类信息
        this.updateById(category);

        // 修改关联信息
        if (!caty.getName().equals(category.getName())){
            log.info("分类名称改变，修改分类关联信息----->修改前：{}，修改后{}", caty.getName(), category.getName());
            CategoryBrandRelationEntity brandRelation = new CategoryBrandRelationEntity();
            brandRelation.setCatelogName(category.getName());
            this.categoryBrandRelationService.update(brandRelation, new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id", category.getCatId()));
        }
    }
}