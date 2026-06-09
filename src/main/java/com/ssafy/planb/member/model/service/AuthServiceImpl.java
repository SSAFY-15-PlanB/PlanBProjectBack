package com.ssafy.planb.member.model.service;

import com.ssafy.planb.member.model.Member;
import com.ssafy.planb.member.model.dto.MemberDto;
import com.ssafy.planb.member.model.mapper.MemberMapper;
import com.ssafy.planb.security.dto.CustomUserDetails;
import com.ssafy.planb.security.util.EncryptUtil;
import com.ssafy.planb.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final MemberMapper memberMapper;
    private final EncryptUtil encryptUtil;


    @Override
    public Map<String, String> login(MemberDto.Login loginInfo) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginInfo.getEmail(), loginInfo.getPassword())
        );

        String userEmail = authentication.getName();
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        // 토큰 발급
        String accessToken = jwtUtil.generateAccessToken(loginInfo.getEmail(), role);
        String refreshToken = jwtUtil.generateRefreshToken(loginInfo.getEmail());

        // DB에 refresh token 저장
        MemberDto.UpdateRefreshToken urt = new MemberDto.UpdateRefreshToken();
        urt.setEmail(userEmail);
        urt.setRefreshToken(refreshToken);
        memberMapper.updateRefreshToken(urt);

        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
    }

    @Override
    public String refresh(String refreshToken) {
        // 토큰 유효성 검증
        if (!jwtUtil.isValid(refreshToken)) return null;

        String userEmail = jwtUtil.getUserEmail(refreshToken);
        MemberDto.Info memberInfo = memberMapper.findMemberInfoByEmail(userEmail);

        // DB의 refresh token과 일치 여부 확인 (탈취 방지)
        if (!refreshToken.equals(memberInfo.getRefreshToken())) return null;

        return jwtUtil.generateAccessToken(userEmail, "MEMBER");
    }

    @Override
    public void logout(String userEmail) {
        // DB에서 refresh token 제거 - 탈취된 토큰으로 재발급 방지
        MemberDto.UpdateRefreshToken urt = new MemberDto.UpdateRefreshToken();
        urt.setEmail(userEmail);
        urt.setRefreshToken(null);
        memberMapper.updateRefreshToken(urt);
    }
}
