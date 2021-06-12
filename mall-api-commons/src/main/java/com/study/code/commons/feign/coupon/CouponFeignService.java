package com.study.code.commons.feign.coupon;

import com.study.code.commons.to.product.SkuReductionTO;
import com.study.code.commons.to.product.SpuBoundsTO;
import com.study.code.utils.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * 保存商品积分Feign
     *
     * 1、CouponFeignService.saveSpuBounds(spuBoundTo);
     *     1）、@RequestBody将这个对象转为json。
     *     2）、找到gulimall-coupon服务，给/coupon/spubounds/save发送请求。
     *          将上一步转的json放在请求体位置，发送请求；
     *     3）、对方服务收到请求。请求体里有json数据。
     *         (@RequestBody SpuBoundsEntity spuBounds)；将请求体的json转为SpuBoundsEntity；
     *     只要json数据模型是兼容的。双方服务无需使用同一个to
     * @param spuBoundsTO
     * @return
     */
    @PostMapping("/coupon/spubounds/feign/saveSpuBounds")
    R saveSpuBounds(@RequestBody SpuBoundsTO spuBoundsTO);

    /**
     * 保存sku的满减优惠信息
     *
     * @param skuReductionTO
     * @return
     */
    @PostMapping("/coupon/skufullreduction/feign/saveSkuReduction")
    R saveSkuReduction(@RequestBody SkuReductionTO skuReductionTO);
}
