package com.study.code.product.service.impl;

import cn.hutool.core.util.ObjectUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.code.commons.util.PageUtils;
import com.study.code.commons.util.Query;

import com.study.code.product.mapper.SpuImagesMapper;
import com.study.code.product.entity.SpuImagesEntity;
import com.study.code.product.service.SpuImagesService;


@Service("spuImagesService")
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesMapper, SpuImagesEntity> implements SpuImagesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuImagesEntity> page = this.page(
                new Query<SpuImagesEntity>().getPage(params),
                new QueryWrapper<SpuImagesEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSpuImages(Long spuId, List<String> images) {
        if (ObjectUtil.isNotEmpty(images)){
            List<SpuImagesEntity> spuImagesList = images.stream()
                    .filter(StringUtils::isNotBlank)
                    .map(image -> {
                        SpuImagesEntity spuImagesEntity = new SpuImagesEntity();
                        spuImagesEntity.setSpuId(spuId);
                        spuImagesEntity.setImgUrl(image);
                        return spuImagesEntity;
                    }).collect(Collectors.toList());
            this.saveBatch(spuImagesList);
        }
    }
}