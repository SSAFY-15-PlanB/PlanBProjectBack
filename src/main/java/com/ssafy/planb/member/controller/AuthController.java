package com.ssafy.planb.member.controller;

import com.ssafy.planb.member.model.Member;
import com.ssafy.planb.member.model.dto.MemberDto;
import com.ssafy.planb.member.model.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDto.Login loginInfo, HttpServletResponse response) {
        Map<String, String> tokens = authService.login(loginInfo);

        // refresh token은 httpOnly 쿠키로 전달 (JS에서 접근 불가)
        setRefreshTokenCookie(response, tokens.get("refreshToken"));

        // access token만 응답 body로 전달
        return ResponseEntity.ok(Map.of(
                "accessToken", tokens.get("accessToken")
        ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@CookieValue(value = "refreshToken", required = false) String refreshToken) {
        // 쿠키에서 refresh token 추출 후 새 access token 발급
        String newAccessToken = authService.refresh(refreshToken);

        if (newAccessToken == null) {
            return ResponseEntity.status(401).body("Invalid refresh token");
        }

        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }

//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(@AuthenticationPrincipal CustomUserDetails userDetails,
//                                    HttpServletResponse response) {
//        // access token에서 추출한 username으로 DB의 refresh token 삭제
//        if (userDetails != null) authService.logout(userDetails.getUsername());
//
//        // 쿠키 만료 처리
//        setRefreshTokenCookie(response, null);
//        return ResponseEntity.ok().build();
//    }

    private void setRefreshTokenCookie(HttpServletResponse response, String value) {
        Cookie cookie = new Cookie("refreshToken", value);
        cookie.setHttpOnly(true);
        cookie.setPath("/api/auth/refresh");
        cookie.setMaxAge(value != null ? 7 * 24 * 60 * 60 : 0);
        response.addCookie(cookie);
    }
}