package com.study.code.product.controller;

import com.study.code.product.entity.CategoryEntity;
import com.study.code.product.service.CategoryService;
import com.study.code.utils.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;



/**
 * 商品三级分类
 *
 * @author suiweidong
 * @email 7334501@qq.com
 * @date 2021-05-06 03:13:45
 */
@Slf4j
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查询所有商品的分类及子类，以树形结构展示
     */
    @RequestMapping("/list/tree")
    public R list(){
        List<CategoryEntity> trees = this.categoryService.queryCategoryTree();

        return R.ok().put("data", trees);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 拖动批量保存
     */
    @RequestMapping("/update/sort")
    public R save(@RequestBody CategoryEntity[] categorys){
        this.categoryService.updateBatchById(Arrays.asList(categorys));

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateDetail(category);

        return R.ok();
    }

    /**
     * 删除
     * @RequestBody: 获取请求体，必须发送POST请求
     * SpringMVC 会自动将请求体的数据 (json)，转为对应的对象
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] catIds){
		// 1、检查当前删除的菜单，是否被别的地方引用
        this.categoryService.removeMenusByCatIds(Arrays.asList(catIds));

        return R.ok();
    }

}
