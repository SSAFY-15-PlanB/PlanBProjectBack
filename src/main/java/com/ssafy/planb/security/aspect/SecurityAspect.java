package com.ssafy.planb.security.aspect;

import com.ssafy.planb.member.model.Member;
import com.ssafy.planb.member.model.dto.MemberDto;
import com.ssafy.planb.security.dto.CustomUserDetails;
import com.ssafy.planb.security.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class SecurityAspect {

    private final EncryptUtil encryptUtil;

    @Around("execution(* com.ssafy.planb.security.service.CustomUserDetailsService.loadUserByUsername(String))")
    public Object manageMemberInfoEncryption(ProceedingJoinPoint joinPoint) throws Throwable {

        // 1. 메서드로 들어온 기존 파라미터(평문 이메일) 가져오기
        Object[] args = joinPoint.getArgs();
        String originalEmail = (String) args[0];

        // 2. 이메일 암호화 후 파라미터 배열 교체 (이래야 실제 메서드에 암호화된 값이 들어갑니다!)
        String encryptedEmail = encryptUtil.aesEncrypt(originalEmail);
        args[0] = encryptedEmail;

        // 3. 암호화된 파라미터를 들고 실제 loadUserByUsername 메서드 실행
        // (조회 시 DB에는 암호화된 이메일이 들어가므로 정상 조회됨)
        Object result = joinPoint.proceed(args);

        // 4. 반환된 객체(UserDetails) 복호화 처리
        if (result instanceof UserDetails) {
            // 본인의 CustomUserDetails 클래스 구조에 맞게 캐스팅 하세요.
            // 여기서는 예시로 result 내부의 MemberDto를 꺼내어 복호화한다고 가정합니다.
            CustomUserDetails userDetails = (CustomUserDetails) result;
            Member member = userDetails.getMember();

            if (member != null && member.getEmail() != null) {
                String decryptedEmail = encryptUtil.aesDecrypt(member.getEmail());
                member.setEmail(decryptedEmail);
            }

            log.info("시큐리티 인증 객체 이메일 복호화 완료");
        }

        // 5. 복호화가 완료된 객체를 스프링 시큐리티 컨텍스트로 반환
        return result;
    }

}
