package com.ssafy.planb.member.model.service;

import com.ssafy.planb.member.model.dto.MemberDto;
import com.ssafy.planb.member.model.enums.Gender;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceImplTest {

    @Autowired
    private MemberService memberService;

    @Test
    void registerMember() {
        MemberDto.Register registerMember = new MemberDto.Register();
        registerMember.setName("최성보");
        registerMember.setEmail("cseongbo17@gmail.com");
        registerMember.setPassword("1234");
        registerMember.setGender(Gender.MALE);
        registerMember.setBirthDate(LocalDate.now());

        memberService.registerMember(registerMember);


    }
}