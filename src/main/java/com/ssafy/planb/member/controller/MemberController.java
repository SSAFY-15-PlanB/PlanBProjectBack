package com.ssafy.planb.member.controller;

import com.ssafy.planb.global.response.ApiResponse;
import com.ssafy.planb.member.model.dto.MemberDto;
import com.ssafy.planb.member.model.mapper.MemberMapper;
import com.ssafy.planb.member.model.service.MemberService;
import com.ssafy.planb.security.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MemberDto.Info>> getMyInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String email = customUserDetails.getUsername();

        System.out.println(email);

        MemberDto.Info info = memberService.searchMemberByEmail(email);

        return ResponseEntity.ok(ApiResponse.success(info));
    }

}
