package com.study.code.member;

import com.study.code.member.entity.MemberEntity;
import com.study.code.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MallMemberApplicationTests {

    @Autowired
    private MemberService memberService;

    @Test
    void contextLoads() {
        MemberEntity member = new MemberEntity();
        member.setCity("成都");

        memberService.save(member);
    }

}
