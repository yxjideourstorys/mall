package com.study.code.commons.vo.product;

import lombok.Data;

/**
 * spu图片
 * @author swd
 */
@Data
public class ImagesVO {

    /**
     * spu图片地址
     */
    private String imgUrl;

    /**
     * 是否是默认图片
     */
    private int defaultImg;
}