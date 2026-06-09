package com.ssafy.planb.member.model.service;

import com.ssafy.planb.member.model.Member;
import com.ssafy.planb.member.model.dto.MemberDto;
import com.ssafy.planb.member.model.mapper.MemberMapper;
import com.ssafy.planb.security.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final EncryptUtil encryptUtil;
    private final MemberMapper memberMapper;

    @Override
    public int registerMember(MemberDto.Register registerInfo) {

        Member m = Member.builder()
                .email(registerInfo.getEmail())
                .name(registerInfo.getName())
                .gender(registerInfo.getGender())
                .password(registerInfo.getPassword())
                .birthDate(registerInfo.getBirthDate())
                .build();

        return memberMapper.saveMember(m);
    }
}
