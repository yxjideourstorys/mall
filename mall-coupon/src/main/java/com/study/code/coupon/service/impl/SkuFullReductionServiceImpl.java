package com.study.code.coupon.service.impl;

import com.study.code.commons.to.product.SkuReductionTO;
import com.study.code.commons.vo.product.MemberPriceVO;
import com.study.code.coupon.entity.MemberPriceEntity;
import com.study.code.coupon.entity.SkuLadderEntity;
import com.study.code.coupon.service.MemberPriceService;
import com.study.code.coupon.service.SkuLadderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.code.commons.util.PageUtils;
import com.study.code.commons.util.Query;

import com.study.code.coupon.mapper.SkuFullReductionMapper;
import com.study.code.coupon.entity.SkuFullReductionEntity;
import com.study.code.coupon.service.SkuFullReductionService;
import org.springframework.transaction.annotation.Transactional;


@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionMapper, SkuFullReductionEntity> implements SkuFullReductionService {

    @Autowired
    private SkuLadderService skuLadderService;

    @Autowired
    private MemberPriceService memberPriceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void saveSkuReduction(SkuReductionTO skuReductionTO) {
        // #6.4.1 阶梯 sms_sku_ladder
        SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
        BeanUtils.copyProperties(skuReductionTO, skuLadderEntity);
        skuLadderEntity.setAddOther(skuReductionTO.getCountStatus());
        this.skuLadderService.saveSkuLadder(skuLadderEntity);

        // #6.4.1 满减 sms_sku_full_reduction
        SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
        BeanUtils.copyProperties(skuReductionTO, skuFullReductionEntity);
        skuFullReductionEntity.setAddOther(skuReductionTO.getPriceStatus());
        this.saveSkuFullReduction(skuFullReductionEntity);

        // #6.4.1 会员价格 sms_member_price
        List<MemberPriceVO> memberPriceVOList = skuReductionTO.getMemberPrice();
        List<MemberPriceEntity> memberPriceEntityList = memberPriceVOList.stream().map(memberPriceVO -> {
            MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
            memberPriceEntity.setMemberLevelId(memberPriceVO.getId());
            memberPriceEntity.setMemberLevelName(memberPriceVO.getName());
            memberPriceEntity.setMemberPrice(memberPriceVO.getPrice());
            memberPriceEntity.setSkuId(skuReductionTO.getSkuId());
            return memberPriceEntity;
        }).collect(Collectors.toList());
        this.memberPriceService.saveBatch(memberPriceEntityList);
    }

    @Override
    public void saveSkuFullReduction(SkuFullReductionEntity skuFullReductionEntity) {
        this.save(skuFullReductionEntity);
    }

}