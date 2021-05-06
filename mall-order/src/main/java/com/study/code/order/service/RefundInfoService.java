package com.study.code.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.code.commons.util.PageUtils;
import com.study.code.order.entity.RefundInfoEntity;

import java.util.Map;

/**
 * 退款信息
 *
 * @author suiweidong
 * @email 7334501@qq.com
 * @date 2021-05-06 02:12:13
 */
public interface RefundInfoService extends IService<RefundInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

