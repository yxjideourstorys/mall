package com.study.code.commons.vo.product;

import lombok.Data;

import java.math.BigDecimal;

/**
 *  设置积分
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