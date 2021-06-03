package com.study.code.commons.feign.coupon;

import com.study.code.utils.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@FeignClient(value = "mall-coupon")
public interface CouponFeignService {

    /**
     * OpenFeign测试
     * @return
     */
    @RequestMapping("/beifeign/test")
    R test();
}
