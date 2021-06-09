package com.study.code.product.vo;

import lombok.Data;

@Data
public class AttrResVO extends AttrReqVO{

    /**
     * 所属分类
     */
    private String catelogName;

    /**
     * 所属分组
     */
    private String groupName;

    /**
     * 分类三级路径
     */
    private Long[] catelogPath;
}
