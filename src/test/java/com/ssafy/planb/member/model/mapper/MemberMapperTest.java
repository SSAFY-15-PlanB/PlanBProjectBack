package com.ssafy.planb.member.model.mapper;

import com.ssafy.planb.member.model.Member;
import com.ssafy.planb.member.model.dto.MemberDto;
import com.ssafy.planb.member.model.enums.Gender;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;

    @Test
    void saveMember() {
        Member m = Member.builder()
                .name("최성보")
                .email("cseongbo17@gmail.com")
                .gender(Gender.MALE)
                .birthDate(LocalDate.now())
                .password("1234")
                .build();

        int i = memberMapper.saveMember(m);
        Assertions.assertEquals(1, i);
    }

    @Test
    void findMemberInfoByEmail() {
        Member m = Member.builder()
                .name("최성보")
                .email("cseongbo17@gmail.com")
                .gender(Gender.MALE)
                .birthDate(LocalDate.now())
                .password("1234")
                .build();

        memberMapper.saveMember(m);

        MemberDto.Info findMember = memberMapper.findMemberInfoByEmail(m.getEmail());

        Assertions.assertEquals(m.getEmail(), findMember.getEmail());
    }

    @Test
    void findMemberByEmail() {
        Member m = Member.builder()
                .name("최성보")
                .email("cseongbo17@gmail.com")
                .gender(Gender.MALE)
                .birthDate(LocalDate.now())
                .password("1234")
                .build();

        memberMapper.saveMember(m);

        Member findMember = memberMapper.findMemberByEmail(m.getEmail());

        Assertions.assertEquals(m.getEmail(), findMember.getEmail());
    }


    @Test
    void updateRefreshToken() {
        MemberDto.UpdateRefreshToken urt = new MemberDto.UpdateRefreshToken();

        urt.setEmail("cseongbo17@gmail.com");
        urt.setRefreshToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjc2VvbmdibzE3QGdtYWlsLmNvbSIsImlhdCI6MTc4MTA2NTUyOSwiZXhwIjoxNzgxNjcwMzI5fQ.O2uXmwPKsiawMc5EsknxFGXzRcjcK4xH6GNmTi7r1LY");

        memberMapper.updateRefreshToken(urt);

        Member memberByEmail = memberMapper.findMemberByEmail("");

        Assertions.assertEquals("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjc2VvbmdibzE3QGdtYWlsLmNvbSIsImlhdCI6MTc4MTA2NTUyOSwiZXhwIjoxNzgxNjcwMzI5fQ.O2uXmwPKsiawMc5EsknxFGXzRcjcK4xH6GNmTi7r1LY", memberByEmail.getRefreshToken());

    }

}