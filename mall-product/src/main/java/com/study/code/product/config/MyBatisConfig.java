package com.study.code.product.config;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
@MapperScan("com.study.code.product.mapper")
public class MyBatisConfig {

    /**
     * 分布插件
     * @return PaginationInnerInterceptor
     */
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor(){
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 设置请求的页面大于最大页后操作，true调回到首页，false继续请求，默认false
        paginationInnerInterceptor.setOverflow(true);
        // 设置最大单面限制数是，默认 500 条，-1不受限
        paginationInnerInterceptor.setMaxLimit(1000L);
        return paginationInnerInterceptor;
    }

}
