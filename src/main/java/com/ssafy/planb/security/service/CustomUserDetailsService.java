package com.ssafy.planb.security.service;

import com.ssafy.planb.member.model.Member;
import com.ssafy.planb.member.model.mapper.MemberMapper;
import com.ssafy.planb.security.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberMapper.findMemberByEmail(email);

        if (member == null) {
            throw new UsernameNotFoundException("존재하지 않는 이메일: " + email);
        }

        return new CustomUserDetails(member);
    }
}
