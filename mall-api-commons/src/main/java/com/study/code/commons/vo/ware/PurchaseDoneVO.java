package com.study.code.commons.vo.ware;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseDoneVO {

    /**
     * 采购单id
     */
    private Long id;

    /**
     * 需求单
     */
    private List<ItemVO> items;

}
