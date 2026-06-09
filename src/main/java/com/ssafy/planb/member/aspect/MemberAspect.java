package com.ssafy.planb.member.aspect;

import com.ssafy.planb.member.model.Member;
import com.ssafy.planb.member.model.dto.MemberDto;
import com.ssafy.planb.security.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class MemberAspect {

    private final EncryptUtil encryptUtil;

    @Before("execution( * com.ssafy.planb.member.model.service.MemberService.registerMember(com.ssafy.planb.member.model.dto.MemberDto$Register)) && args(registerInfo)")
    public void encodeMemberInfo(MemberDto.Register registerInfo) {

        registerInfo.setEmail(encryptUtil.aesEncrypt(registerInfo.getEmail()));
        registerInfo.setPassword(encryptUtil.encryptPwd(registerInfo.getPassword()));

    }
}
