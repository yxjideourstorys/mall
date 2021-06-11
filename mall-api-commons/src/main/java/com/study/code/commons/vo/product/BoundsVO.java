package com.study.code.commons.vo.product;

import lombok.Data;

import java.math.BigDecimal;

/**
 *
 * @author swd
 */
@Data
public class BoundsVO {
    /**
     * 购物积分
     */
    private BigDecimal buyBounds;

    /**
     * 成长积分
     */
    private BigDecimal growBounds;
}