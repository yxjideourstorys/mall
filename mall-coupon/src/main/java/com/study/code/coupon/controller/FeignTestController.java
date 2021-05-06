package com.study.code.coupon.controller;

import com.study.code.utils.util.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("beifeign")
public class FeignTestController {

    @RequestMapping("/test")
    public R test(){
        Map<String,Object> map = new HashMap<>();
        map.put("哈哈", "满100减1000");
        return R.ok().put("优惠券被调用了", map);
    }
}
