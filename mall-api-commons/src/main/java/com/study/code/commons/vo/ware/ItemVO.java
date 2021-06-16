package com.study.code.commons.vo.ware;

import lombok.Data;

@Data
public class ItemVO {

    /**
     * 采购需求id
     */
    private Long itemId;

    /**
     * 采购需求状态
     */
    private Integer status;

//    /**
//     * 商品单价
//     */
//    private Integer skuPrice;

    /**
     * 失败原因
     */
    private String reason;


}
