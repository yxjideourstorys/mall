package com.study.code.commons.vo.product;

import lombok.Data;

/**
 * sku属性
 * @author swd
 *
 **/
@Data
public class AttrVO {
    /**
     * sku属性id
     */
    private Long attrId;

    /**
     * sku属性名
     */
    private String attrName;

    /**
     * sku属性值
     */
    private String attrValue;
}