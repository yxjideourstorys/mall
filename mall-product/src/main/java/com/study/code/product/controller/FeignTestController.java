package com.study.code.product.controller;

import com.study.code.commons.feign.coupon.CouponFeignService;
import com.study.code.utils.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/diaofeign")
public class FeignTestController {

    @Autowired
    private CouponFeignService couponFeignService;

    @RequestMapping("/test")
    public R test(){
        return this.couponFeignService.test();
    }
}
