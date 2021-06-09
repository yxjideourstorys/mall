package com.study.code.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.study.code.commons.valid.AddGroup;
import com.study.code.commons.valid.UpdateGroup;
import com.study.code.commons.valid.UpdateStatusGroup;
import com.study.code.commons.valid.diy.ListValue;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 品牌
 *
 * @author suiweidong
 * @email 7334501@qq.com
 * @date 2021-05-06 03:13:45
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    @NotNull(message = "修改必须指定品牌id", groups = {UpdateGroup.class})
    @Null(message = "新增不能指定品牌id", groups = {AddGroup.class})
    @TableId
    private Long brandId;

    /**
     * 品牌名
     */
    @NotBlank(message = "品牌名不能为空", groups = {UpdateGroup.class, AddGroup.class})
    private String name;

    /**
     * 品牌logo地址
     */
    @NotEmpty(message = "URL地址不能为空", groups = {AddGroup.class})
    @URL(message = "您输入的URL地址不合法", groups = {AddGroup.class, UpdateGroup.class})
    private String logo;

    /**
     * 介绍
     */
    private String descript;

    /**
     * 显示状态[0-不显示；1-显示]
     */
    @NotNull(message = "显示状态不能为空", groups = {AddGroup.class, UpdateStatusGroup.class})
    @ListValue(vals = {0, 1}, message = "状态不正确", groups = {AddGroup.class, UpdateStatusGroup.class})
    private Integer showStatus;

    /**
     * 检索首字母
     */
    @NotEmpty(message = "检索首字母不能为空", groups = {AddGroup.class})
    @Pattern(regexp = "^[a-zA-Z]$", message = "检索首字母必须是一个字母", groups = {AddGroup.class, UpdateGroup.class})
    private String firstLetter;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空", groups = {AddGroup.class})
    @Min(value = 0, message = "排序必须大于等于0", groups = {AddGroup.class, UpdateGroup.class})
    private Integer sort;

}
