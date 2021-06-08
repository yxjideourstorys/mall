package com.study.code.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
	@TableId
	private Long brandId;

	/**
	 * 品牌名
	 */
	@NotBlank(message = "品牌名不能为空")
	private String name;

	/**
	 * 品牌logo地址
	 */
	@NotEmpty(message = "URL地址不能为空")
	@URL(message = "您输入的URL地址不合法")
	private String logo;

	/**
	 * 介绍
	 */
	private String descript;

	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	private Integer showStatus;

	/**
	 * 检索首字母
	 */
	@NotEmpty()
	@Pattern(regexp="^[a-zA-Z]$",message = "检索首字母必须是一个字母")
	private String firstLetter;

	/**
	 * 排序
	 */
	@NotNull()
	@Min(value = 0,message = "排序必须大于等于0")
	private Integer sort;

}
