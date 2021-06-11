package com.study.code.commons.vo.product;

import lombok.Data;

import java.math.BigDecimal;

/**
 *
 * @author swd
 */
@Data
public class MemberPriceVO {

    /**
     * 会员等级id
     */
    private Long id;

    /**
     *  会员等级名字
     */
    private String name;

    /**
     *  会员等级价格
     */
    private BigDecimal price;
}