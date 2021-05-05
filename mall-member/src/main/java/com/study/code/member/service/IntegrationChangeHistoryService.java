package com.study.code.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.code.common.utils.PageUtils;
import com.study.code.member.entity.IntegrationChangeHistoryEntity;

import java.util.Map;

/**
 * 积分变化历史记录
 *
 * @author suiweidong
 * @email 7334501@qq.com
 * @date 2021-05-06 04:48:01
 */
public interface IntegrationChangeHistoryService extends IService<IntegrationChangeHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

