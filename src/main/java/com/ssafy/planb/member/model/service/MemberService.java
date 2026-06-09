package com.ssafy.planb.member.model.service;

import com.ssafy.planb.member.model.dto.MemberDto;

public interface MemberService {
    int registerMember(MemberDto.Register registerInfo);
    MemberDto.Info searchMemberByEmail(String email);
}
