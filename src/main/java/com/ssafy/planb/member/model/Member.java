package com.ssafy.planb.member.model;

import com.ssafy.planb.member.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Member {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Gender gender;
    private LocalDate birthDate;
    private String refreshToken;

    @Builder
    public Member (String name, String email, String password, Gender gender, LocalDate birthDate) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }





}
