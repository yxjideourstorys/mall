package com.study.code.product.controller;

import com.study.code.commons.feign.coupon.CouponFeignService;
import com.study.code.utils.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/diaofeign")
public class FeignTestController {

    @Autowired
    private CouponFeignService couponFeignService;

    @Value("${dgg.info}")
    private String info;

    @RequestMapping("/test")
    public R test(){
        return this.couponFeignService.test();
    }

    @RequestMapping("/info")
    public R info(){
        return R.ok(info);
    }
}
