package com.ssafy.planb.member.model.dto;

import com.ssafy.planb.member.model.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

public class MemberDto {

    @Getter
    @Setter
    public static class Info {
        private Long id;
        private String name;
        private String email;
        private Gender gender;
        private LocalDate birthDate;
        private String refreshToken;
    }

    @Getter
    public static class Login {
        private String email;
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UpdateRefreshToken {
        private String email;
        private String refreshToken;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Register {
        private String name;
        private String email;
        private String password;
        private Gender gender;
        private LocalDate birthDate;

    }

}
