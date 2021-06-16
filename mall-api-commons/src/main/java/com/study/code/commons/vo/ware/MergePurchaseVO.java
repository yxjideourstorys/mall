package com.study.code.commons.vo.ware;

import lombok.Data;

import java.util.List;

@Data
public class MergePurchaseVO {
    /**
     * 采购单id
     */
    private Long purchaseId;

    /**
     * 仓库id
     */
    private Long wareId;

    /**
     * 采购需求id
     */
    private List<Long> items;
}
