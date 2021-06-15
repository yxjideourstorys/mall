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

import com.study.code.ware.mapper.WareOrderTaskMapper;
import com.study.code.ware.entity.WareOrderTaskEntity;
import com.study.code.ware.service.WareOrderTaskService;


@Service("wareOrderTaskService")
public class WareOrderTaskServiceImpl extends ServiceImpl<WareOrderTaskMapper, WareOrderTaskEntity> implements WareOrderTaskService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<WareOrderTaskEntity> queryWrapper = new QueryWrapper<>();
        String key = MapUtil.getStr(params, "key");
        if (StringUtils.isNotEmpty(key)){
            queryWrapper.eq("id", key)
                    .or().eq("order_id", key)
                    .or().eq("tracking_no", key)
                    .or().eq("ware_id", key)
                    .or().eq("consignee_tel", key)
                    .or().like("name", key)
                    .or().like("address", key)
                    .or().like("consignee", key);
        }

        IPage<WareOrderTaskEntity> page = this.page(
                new Query<WareOrderTaskEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

}