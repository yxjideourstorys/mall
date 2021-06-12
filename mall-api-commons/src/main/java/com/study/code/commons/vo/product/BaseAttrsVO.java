package com.study.code.commons.vo.product;

import lombok.Data;

/**
 * 规格参数(分组及属性)
 * @author swd
 */
@Data
public class BaseAttrsVO {

    /**
     * 属性id
     */
    private Long attrId;

    /**
     *  属性值
     */
    private String attrValues;

    /**
     * 是否快速显示
     */
    private int showDesc;

}