package com.study.code.product.service.impl;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.study.code.product.entity.AttrEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.code.commons.util.PageUtils;
import com.study.code.commons.util.Query;

import com.study.code.product.mapper.AttrGroupMapper;
import com.study.code.product.entity.AttrGroupEntity;
import com.study.code.product.service.AttrGroupService;

@Slf4j
@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByCatId(Map<String, Object> params, Long catId) {
        log.info("param：" + JSON.toJSONString(params, SerializerFeature.PrettyFormat));
        log.info("catId：" + catId);
        String key = MapUtil.getStr(params, "key");

        QueryWrapper<AttrGroupEntity> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(key)){
            queryWrapper.and(objectQueryWrapper -> {
                objectQueryWrapper.eq("attr_group_id", key).or().like("attr_group_name", key);
            });
        }

        if (catId == 0) {
            // 说明未打开分类查询
            return new PageUtils(this.page(new Query<AttrGroupEntity>().getPage(params), queryWrapper));
        } else {
            // 按分类查询
            queryWrapper.eq("catelog_id", catId);
            return new PageUtils(this.page(new Query<AttrGroupEntity>().getPage(params), queryWrapper));
        }
    }
}