package com.study.code.member.service.impl;

import cn.hutool.core.map.MapUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.code.commons.util.PageUtils;
import com.study.code.commons.util.Query;

import com.study.code.member.mapper.MemberLevelMapper;
import com.study.code.member.entity.MemberLevelEntity;
import com.study.code.member.service.MemberLevelService;


@Service("memberLevelService")
public class MemberLevelServiceImpl extends ServiceImpl<MemberLevelMapper, MemberLevelEntity> implements MemberLevelService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberLevelEntity> page = this.page(
                new Query<MemberLevelEntity>().getPage(params),
                new QueryWrapper<MemberLevelEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryMemLevelPageList(Map<String, Object> params) {
        QueryWrapper<MemberLevelEntity> queryWrapper = new QueryWrapper<>();
        String key = MapUtil.getStr(params, "key");
        if (StringUtils.isNotEmpty(key)){
            queryWrapper.eq("id", key).or().like("name", key);
        }

        IPage<MemberLevelEntity> page = this.page(
                new Query<MemberLevelEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }
}