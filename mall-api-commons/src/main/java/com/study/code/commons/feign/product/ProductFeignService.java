package com.study.code.commons.feign.product;

import com.study.code.utils.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@FeignClient(value = "mall-product")
public interface ProductFeignService {

    /**
     * 通过skuId查询sku信息
     *
     *  注意：// @RequestMapping("/product/skuinfo/info/{skuId}")  不走网关
     *       // @RequestMapping("/api/product/skuinfo/info/{skuId}")  走网关
     *
     * @param skuId 商品id
     * @return R
     */
    @RequestMapping("/product/skuinfo/info/{skuId}")
    R skuInfo(@PathVariable("skuId") Long skuId);

}
