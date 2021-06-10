package com.study.code.product.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.code.commons.constant.ProductConstant;
import com.study.code.commons.util.PageUtils;
import com.study.code.commons.util.Query;
import com.study.code.product.entity.AttrAttrgroupRelationEntity;
import com.study.code.product.entity.AttrEntity;
import com.study.code.product.entity.AttrGroupEntity;
import com.study.code.product.mapper.AttrAttrgroupRelationMapper;
import com.study.code.product.mapper.AttrGroupMapper;
import com.study.code.product.mapper.AttrMapper;
import com.study.code.product.service.AttrAttrgroupRelationService;
import com.study.code.product.service.AttrGroupService;
import com.study.code.product.vo.AttrGroupReqVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroupEntity> implements AttrGroupService {


    @Autowired
    private AttrMapper attrMapper;

    @Autowired
    private AttrAttrgroupRelationMapper attrgroupRelationMapper;

    @Autowired
    private AttrAttrgroupRelationService attrgroupRelationService;


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
        if (StringUtils.isNotEmpty(key)) {
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

    @Override
    public PageUtils queryNoattrRelationList(Map<String, Object> params, Long attrGroupId) {
        // 通过分组id查询分类信息
        AttrGroupEntity attrGroup = this.getById(attrGroupId);
        Long catelogId = attrGroup.getCatelogId();
        log.info("queryNoattRelationList------------>catelogId:{}", catelogId);

        // 通过catelogId 查询该分类下有多少分组
        List<Long> attrGroupIds = this.baseMapper.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId))
                .stream()
                .map(AttrGroupEntity::getAttrGroupId)
                .collect(Collectors.toList());
        log.info("queryNoattRelationList------------>attrGroupIds:{}", attrGroupIds);

        // 通过分组id 查询分组下有多少属性已绑定数据
        List<AttrAttrgroupRelationEntity> relationList = this.attrgroupRelationMapper.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", attrGroupIds));
        List<Long> attrIds = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(relationList)) {
            attrIds = relationList.stream()
                    .map(AttrAttrgroupRelationEntity::getAttrId)
                    .collect(Collectors.toList());
        }
        log.info("queryNoattRelationList------------>attrIds:{}", attrIds);


        // 通过分类id查询所有的属性，并排除已绑定的属性值
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>().eq("catelog_id", catelogId).eq("attr_type", ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        if (ObjectUtil.isNotEmpty(attrIds)) {
            queryWrapper.notIn("attr_id", attrIds);
        }

        String key = MapUtil.getStr(params, "key");
        if (StringUtils.isNotEmpty(key)) {
            queryWrapper.eq("attr_id", key)
                    .or()
                    .like("attr_name", key);
        }

        IPage<AttrEntity> attrPage = this.attrMapper.selectPage(new Query<AttrEntity>().getPage(params), queryWrapper);
        log.info("queryNoattRelationList------------>attrPage:{}", JSON.toJSONString(attrPage, SerializerFeature.PrettyFormat));
        return new PageUtils(attrPage);
    }

    @Override
    public void saveAttrRelation(AttrGroupReqVO[] attrGroupReqVO) {
        List<AttrGroupReqVO> attrGroupReqVOS = Arrays.asList(attrGroupReqVO);
        log.info("saveAttrRelation--->" + attrGroupReqVOS);

        List<AttrAttrgroupRelationEntity> relationEntities = attrGroupReqVOS.stream().map(item -> {
            AttrAttrgroupRelationEntity attrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, attrgroupRelationEntity);
            return attrgroupRelationEntity;
        }).collect(Collectors.toList());

        Integer count = this.attrgroupRelationMapper.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_id", attrGroupReqVOS.stream().map(AttrGroupReqVO::getAttrId).collect(Collectors.toList())));
        if (count > 0) {
            log.info("saveAttrRelation--->更新操作---> attrgroupRelationService.updateBatchById--->" + JSON.toJSONString(relationEntities, SerializerFeature.PrettyFormat));
            this.attrgroupRelationMapper.updateBatchByAttrId(relationEntities);
        } else {
            log.info("saveAttrRelation--->插入操作---> attrgroupRelationService.saveBatch--->" + JSON.toJSONString(relationEntities, SerializerFeature.PrettyFormat));
            this.attrgroupRelationService.saveBatch(relationEntities);
        }
    }

    @Override
    public List<AttrEntity> queryAttrRelationList(Long attrGroupId) {
        log.info("queryAttrRelationList--->attrGroupId：{}", attrGroupId);

        // 通过分组id查询属性
        List<AttrAttrgroupRelationEntity> relationEntities = this.attrgroupRelationMapper.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrGroupId));
        List<AttrEntity> attrEntities = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(relationEntities)){
            List<Long> attrIds = relationEntities.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
            attrEntities = this.attrMapper.selectList(new QueryWrapper<AttrEntity>().in("attr_id", attrIds));
        }

        log.info("queryAttrRelationList--->" + JSON.toJSONString(attrEntities, SerializerFeature.PrettyFormat));
        return attrEntities;
    }

    @Override
    public void attrRelationDelete(List<AttrGroupReqVO> asList) {
        log.info("attrRelationDelete--->param：" + JSON.toJSONString(asList, SerializerFeature.PrettyFormat));
        List<AttrAttrgroupRelationEntity> relationList = asList.stream().map(item -> {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());

        log.info("attrRelationDelete--->attrgroupRelationMapper.attrRelationDelete：" + JSON.toJSONString(relationList, SerializerFeature.PrettyFormat));
        this.attrgroupRelationMapper.attrRelationDelete(relationList);
    }
}