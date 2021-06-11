package com.study.code.product.controller;

import com.study.code.commons.util.PageUtils;
import com.study.code.commons.vo.product.AttrResVO;
import com.study.code.product.entity.AttrEntity;
import com.study.code.product.entity.AttrGroupEntity;
import com.study.code.product.service.AttrAttrgroupRelationService;
import com.study.code.product.service.AttrGroupService;
import com.study.code.product.service.CategoryService;
import com.study.code.commons.vo.product.AttrGroupAndAttrResVO;
import com.study.code.commons.vo.product.AttrGroupReqVO;
import com.study.code.utils.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 * 属性分组
 *
 * @author suiweidong
 * @email 7334501@qq.com
 * @date 2021-05-06 03:13:45
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrAttrgroupRelationService relationService;

    /**
     * 列表
     */
    @RequestMapping("/list/{catId}")
    public R list(@RequestParam Map<String, Object> params, @PathVariable("catId") Long catId){

        PageUtils page = attrGroupService.queryPageByCatId(params, catId);

        return R.ok().put("page", page);
    }

    /**
     * 属性未关联列表
     */
    @RequestMapping("/{attrGroupId}/noattr/relation")
    public R noattrRelation(@RequestParam Map<String, Object> params, @PathVariable("attrGroupId") Long attrGroupId){

        PageUtils page = attrGroupService.queryNoattrRelationList(params, attrGroupId);

        return R.ok().put("page", page);
    }

    /**
     * 属性关联列表
     */
    @RequestMapping("/{attrGroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrGroupId") Long attrGroupId){

        List<AttrResVO> result = attrGroupService.queryAttrRelationList(attrGroupId);

        return R.ok().put("data", result);
    }


    /**
     * 根据分类id查询该分类下品牌及属性列表
     */
    @GetMapping("/{catelogId}/withattr")
    public R brandsWithattr(@PathVariable(value = "catelogId") Long catId){
        List<AttrGroupAndAttrResVO> brandsWithattr = this.attrGroupService.brandsWithattr(catId);

        return R.ok().put("data", brandsWithattr);
    }

    /**
     * 保存
     */
    @RequestMapping("/attr/relation")
    public R saveAttrRelation(@RequestBody AttrGroupReqVO[] attrGroupReqVO){
        attrGroupService.saveAttrRelation(attrGroupReqVO);

        return R.ok();
    }

    /**
     * 关联删除
     */
    @RequestMapping("/attr/relation/delete")
    public R attrRelationDelete(@RequestBody AttrGroupReqVO[] attrGroupReqVO){
        attrGroupService.attrRelationDelete(Arrays.asList(attrGroupReqVO));

        return R.ok();
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);

		Long[] path = this.categoryService.getCategoryPath(attrGroup.getCatelogId());
		attrGroup.setCatelogPath(path);

        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
