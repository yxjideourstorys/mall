package com.study.code.commons.to.product;

import com.study.code.commons.vo.product.MemberPriceVO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SkuReductionTO implements Serializable {

    /**
     * sku_id
     */
    private Long skuId;

    /**
     * 满几件
     */
    private int fullCount;

    /**
     * 打几折
     */
    private BigDecimal discount;

    /**
     * 是否叠加其它数量优惠
     */
    private int countStatus;

    /**
     * 满多少钱
     */
    private BigDecimal fullPrice;

    /**
     * 打几折
     */
    private BigDecimal reducePrice;

    /**
     * 是否叠加其它价钱优惠
     */
    private int priceStatus;

    /**
     *  会员价格
     */
    private List<MemberPriceVO> memberPrice;
}
