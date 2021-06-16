package com.study.code.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.code.commons.util.PageUtils;
import com.study.code.commons.util.Query;

import com.study.code.product.mapper.ProductAttrValueMapper;
import com.study.code.product.entity.ProductAttrValueEntity;
import com.study.code.product.service.ProductAttrValueService;


@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueMapper, ProductAttrValueEntity> implements ProductAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new Query<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveProductAttrValue(List<ProductAttrValueEntity> productAttrValueEntityList) {
        this.saveBatch(productAttrValueEntityList);
    }

    @Override
    public List<ProductAttrValueEntity> listforspu(Long spuId) {
        return this.baseMapper.selectList(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId));
    }

    @Override
    public void updateSpuAttr(Long spuId, List<ProductAttrValueEntity> productAttrValues) {
        // 根据spuId，删除spu属性值
        this.baseMapper.delete(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId));

        // 批量插入新的spu属性值
        List<ProductAttrValueEntity> productAttrValueEntityList = productAttrValues.stream().map(attrValue -> {
            attrValue.setSpuId(spuId);
            return attrValue;
        }).collect(Collectors.toList());
        this.saveBatch(productAttrValueEntityList);
    }

}