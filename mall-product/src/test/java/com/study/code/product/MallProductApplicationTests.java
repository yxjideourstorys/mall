package com.study.code.product;

import com.study.code.product.entity.BrandEntity;
import com.study.code.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MallProductApplicationTests {

    @Autowired
    private BrandService brandService;

    @Test
    void contextLoads() {
        BrandEntity brand = new BrandEntity();
        brand.setDescript("哈哈");

        brandService.save(brand);
    }
}
