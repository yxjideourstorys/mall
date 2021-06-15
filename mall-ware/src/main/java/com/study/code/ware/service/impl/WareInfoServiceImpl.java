package com.study.code.ware.service.impl;

import cn.hutool.core.map.MapUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.code.commons.util.PageUtils;
import com.study.code.commons.util.Query;

import com.study.code.ware.mapper.WareInfoMapper;
import com.study.code.ware.entity.WareInfoEntity;
import com.study.code.ware.service.WareInfoService;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoMapper, WareInfoEntity> implements WareInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<WareInfoEntity> querywrapper = new QueryWrapper<>();
        String key = MapUtil.getStr(params, "key");
        if (StringUtils.isNotEmpty(key)){
            querywrapper.eq("id", key)
                    .or().eq("areacode", key)
                    .or().like("name", key)
                    .or().like("address", key);
        }

        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),
                querywrapper
        );

        return new PageUtils(page);
    }

}