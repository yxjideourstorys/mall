package com.study.code.product.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.study.code.commons.valid.AddGroup;
import com.study.code.commons.valid.UpdateGroup;
import com.study.code.commons.valid.UpdateStatusGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.study.code.product.entity.BrandEntity;
import com.study.code.product.service.BrandService;
import com.study.code.commons.util.PageUtils;
import com.study.code.utils.util.R;

import javax.validation.Valid;


/**
 * 品牌
 *
 * @author suiweidong
 * @email 7334501@qq.com
 * @date 2021-05-06 03:13:45
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@Validated(AddGroup.class) @RequestBody BrandEntity brand){
//    public R save(@Valid @RequestBody BrandEntity brand, BindingResult result){
//        if (result.hasErrors()){
//            Map<String, String> map = new HashMap<>();
//            // 获取校验错误结果
//            result.getFieldErrors().forEach(item -> {
//                // FieldError 获取到错误提示
//                String message = item.getDefaultMessage();
//                // 获取错误属性的名字
//                String field = item.getField();
//                map.put(field, message);
//            });
//            return R.error(400, "提交的数据不合法").put("data", map);
//        }else {
//            brandService.save(brand);
//        }

        brandService.save(brand);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@Validated(UpdateGroup.class) @RequestBody BrandEntity brand){
		brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 修改状态
     */
    @RequestMapping("/status")
    public R status(@Validated(UpdateStatusGroup.class) @RequestBody BrandEntity brand){
        brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
