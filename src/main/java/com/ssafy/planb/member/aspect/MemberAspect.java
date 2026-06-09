package com.ssafy.planb.member.aspect;

import com.ssafy.planb.member.model.Member;
import com.ssafy.planb.member.model.dto.MemberDto;
import com.ssafy.planb.security.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
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

//    @Before("execution( * com.ssafy.planb.member.model.service.MemberService.searchMemberByEmail(String)) && args(email)")
//    public void encodeEmail(String email) {
//        email = encryptUtil.aesEncrypt(email);
//    }
    @Around("execution(* com.ssafy.planb.member.model.service.MemberService.searchMemberByEmail(String))")
    public Object manageMemberSearchEncryption(ProceedingJoinPoint joinPoint) throws Throwable {

        // 1. 메서드로 들어온 기존 파라미터(평문 이메일) 가져오기
        Object[] args = joinPoint.getArgs();
        String originalEmail = (String) args[0];

        // 2. 이메일 암호화 후 파라미터 배열 교체 (실제 서비스/매퍼에 암호화된 값이 전달됨)
        String encryptedEmail = encryptUtil.aesEncrypt(originalEmail);
        args[0] = encryptedEmail;

        // 3. 암호화된 파라미터를 들고 실제 searchMemberByEmail 메서드 실행
        Object result = joinPoint.proceed(args);

        // 4. 반환된 회원 객체의 이메일을 다시 평문으로 복호화 처리
        if (result != null) {

            // 상황 1: 반환 타입이 Member 객체일 경우
            if (result instanceof MemberDto.Info) {
                MemberDto.Info memberInfo = (MemberDto.Info) result;
                if (memberInfo.getEmail() != null) {
                    String decryptedEmail = encryptUtil.aesDecrypt(memberInfo.getEmail());
                    memberInfo.setEmail(decryptedEmail);
                }
            }

            log.info("회원 검색 결과 이메일 복호화 완료");
        }

        // 5. 복호화가 완료된 객체를 서비스 호출부로 반환
        return result;
    }


}
