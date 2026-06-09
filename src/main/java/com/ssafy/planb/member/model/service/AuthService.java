package com.ssafy.planb.member.model.service;

import com.ssafy.planb.member.model.dto.MemberDto;

import java.util.Map;

public interface AuthService {
    Map<String, String> login(MemberDto.Login loginInfo);
    String refresh(String refreshToken);
    void logout(String username);
}
