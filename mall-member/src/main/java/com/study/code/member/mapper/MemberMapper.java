package com.study.code.member.mapper;

import com.study.code.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author suiweidong
 * @email 7334501@qq.com
 * @date 2021-05-06 04:48:01
 */
@Mapper
public interface MemberMapper extends BaseMapper<MemberEntity> {
	
}
