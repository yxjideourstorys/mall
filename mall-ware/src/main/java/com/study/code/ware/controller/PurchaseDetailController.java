package com.study.code.ware.controller;

import java.util.Arrays;
import java.util.Map;

import com.study.code.commons.constant.WareConstant;
import com.study.code.commons.exception.BizCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.study.code.ware.entity.PurchaseDetailEntity;
import com.study.code.ware.service.PurchaseDetailService;
import com.study.code.commons.util.PageUtils;
import com.study.code.utils.util.R;



/**
 * 
 *
 * @author suiweidong
 * @email 7334501@qq.com
 * @date 2021-05-06 04:53:45
 */
@RestController
@RequestMapping("ware/purchasedetail")
public class PurchaseDetailController {
    @Autowired
    private PurchaseDetailService purchaseDetailService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseDetailService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		PurchaseDetailEntity purchaseDetail = purchaseDetailService.getById(id);

        return R.ok().put("purchaseDetail", purchaseDetail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PurchaseDetailEntity purchaseDetail){
		purchaseDetailService.save(purchaseDetail);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PurchaseDetailEntity purchaseDetail){

        if (purchaseDetail.getStatus() != WareConstant.PurchaseDetailEnum.CREATED.getCode()){
            return R.error(BizCodeEnum.STATUS_EXCEPTION.getCode(), "该状态下不可进行修改");
        }

		purchaseDetailService.updateById(purchaseDetail);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		purchaseDetailService.removePurchaseDetailByIds(Arrays.asList(ids));

        return R.ok();
    }

}
