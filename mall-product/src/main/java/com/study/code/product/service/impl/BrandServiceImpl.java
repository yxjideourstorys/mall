package com.study.code.product.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.code.commons.exception.BizException;
import com.study.code.commons.util.PageUtils;
import com.study.code.commons.util.Query;
import com.study.code.product.entity.BrandEntity;
import com.study.code.product.entity.CategoryBrandRelationEntity;
import com.study.code.product.mapper.BrandMapper;
import com.study.code.product.service.BrandService;
import com.study.code.product.service.CategoryBrandRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandMapper, BrandEntity> implements BrandService {

    @Autowired
    private CategoryBrandRelationService brandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                new QueryWrapper<BrandEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void updateDetail(BrandEntity brand) {
        BrandEntity bra = getById(brand.getBrandId());
        if (ObjectUtil.isEmpty(bra)){
            throw new BizException("该品牌id【"+ brand.getBrandId() +"】的品牌信息不存在");
        }

        // 更新品牌信息
        this.updateById(brand);

        if (!bra.getName().equals(brand.getName())){
            log.info("品牌信息名变化，修改关联信息表----->修改前：{}，修改后：{}", bra.getName(), brand.getName());
            // 品牌信息名变化，修改关联信息表
            CategoryBrandRelationEntity brandRelation = new CategoryBrandRelationEntity();
            brandRelation.setBrandName(brand.getName());
            this.brandRelationService.update(brandRelation, new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id", brand.getBrandId()));
        }
    }
}