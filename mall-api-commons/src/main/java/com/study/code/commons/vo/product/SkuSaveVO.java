package com.study.code.commons.vo.product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品保存vo
 * @author swd
 */
@Data
public class SkuSaveVO {

    /**
     * 商品名称
     */
    private String spuName;

    /**
     * 商品描述
     */
    private String spuDescription;

    /**
     * 分类id
     */
    private Long catalogId;

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 上架状态[0 - 下架，1 - 上架]
     */
    private int publishStatus;

    /**
     * 商品介绍 ，逗号隔开
     */
    private List<String> decript;

    /**
     * spu图片集
     */
    private List<String> images;

    /**
     *
     */
    private BoundsVO bounds;

    /**
     *
     */
    private List<BaseAttrsVO> baseAttrs;

    /**
     * 所有sku信息
     */
    private List<SkusVO> skus;
}