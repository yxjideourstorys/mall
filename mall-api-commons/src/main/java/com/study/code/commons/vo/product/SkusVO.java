package com.study.code.commons.vo.product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 所有sku信息
 * @author swd
 */
@Data
public class SkusVO {

    /**
     * 属性组合
     */
    private List<AttrVO> attr;

    /**
     * 商品名称
     */
    private String skuName;

    /**
     *  价格
     */
    private String price;

    /**
     * 标题
     */
    private String skuTitle;

    /**
     * 副标题
     */
    private String skuSubtitle;

    /**
     * sku图片集
     */
    private List<ImagesVO> images;

    /**
     * 笛卡尔积 ["星空黑","12gb","pro"]
     */
    private List<String> descar;

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