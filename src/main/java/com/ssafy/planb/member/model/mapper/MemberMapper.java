package com.ssafy.planb.member.model.mapper;

import com.ssafy.planb.member.model.Member;
import com.ssafy.planb.member.model.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    int saveMember(Member member);
    Member findMemberByEmail(String email);
    MemberDto.Info findMemberInfoByEmail(String email);
    MemberDto.Info findMemberInfoById(Long id);
    int updateRefreshToken(MemberDto.UpdateRefreshToken urt);



}
