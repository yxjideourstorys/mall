package com.study.code.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.code.commons.util.PageUtils;
import com.study.code.commons.util.Query;
import com.study.code.product.entity.BrandEntity;
import com.study.code.product.entity.CategoryBrandRelationEntity;
import com.study.code.product.entity.CategoryEntity;
import com.study.code.product.mapper.CategoryBrandRelationMapper;
import com.study.code.product.service.BrandService;
import com.study.code.product.service.CategoryBrandRelationService;
import com.study.code.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationMapper, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelationEntity relation) {
        BrandEntity brand = this.brandService.getById(relation.getBrandId());
        CategoryEntity category = categoryService.getById(relation.getCatelogId());

        relation.setBrandName(brand.getName());
        relation.setCatelogName(category.getName());
        this.save(relation);
    }
}