package com.ssafy.planb.common.service;

import com.ssafy.planb.common.util.RedisUtil;
import com.ssafy.planb.global.exceptions.CustomException;
import com.ssafy.planb.global.exceptions.ErrorCode;
import com.ssafy.planb.member.model.Member;
import com.ssafy.planb.member.model.mapper.MemberMapper;
import com.ssafy.planb.security.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class EmailService {

    private final JavaMailSender mailSender;
    private final RedisUtil redisUtil;
    private final MemberMapper memberMapper;
    private final EncryptUtil encryptService;

    @Value("${spring.mail.username}")
    private String configEmail;


    private String createCode() {
        int code = (int) (Math.random() * 900000) + 100000;

        return String.valueOf(code);
    }

    private MimeMessage createEmailForm(String email) throws MessagingException {
        String authCode = createCode();


        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("PlanB 회원가입 인증번호 발송");

        String msg = ""
                + "<div style=\"font-family: Arial, sans-serif; padding: 30px; background-color: #1e1e1e; color: #fff;\">"
                + "  <h1 style=\"font-size: 28px; color: white;\">이메일 인증번호 안내</h1>"
                + "  <p style=\"font-size: 16px; margin-top: 24px; line-height: 1.6;\">"
                + "    본 메일은 <strong>PlanB</strong> 사이트의 회원가입을 위한 이메일 인증입니다.<br>"
                + "    아래의 <strong>[이메일 인증번호]</strong>를 입력하여 본인확인을 해주시기 바랍니다."
                + "  </p>"
                + "  <div style=\"background-color: #2a2a2a; padding: 30px; margin-top: 30px; margin-bottom: 40px; border-radius: 8px; text-align: center;\">"
                + "    <span style=\"font-size: 32px; font-weight: bold; letter-spacing: 3px; color: white;\">" + authCode + "</span>"
                + "  </div>"
                + "  <p style=\"font-size: 14px; color: #ccc;\">감사합니다.<br>PlanB 담당 드림</p>"
                + "</div>";
        message.setText(msg, "utf-8", "html");

        message.setFrom(configEmail);

        redisUtil.setDataWithTTL(email, authCode, 60 * 3L);

        return message;

    }

    public void sendVerificationCode(String toEmail) throws MessagingException {

        String encryptedEmail = encryptService.aesEncrypt(toEmail);
        Optional<Member> memberByEmail = Optional.ofNullable(memberMapper.findMemberByEmail(encryptedEmail));
        if (memberByEmail.isPresent()) throw new CustomException(ErrorCode.DUPLICATE_EMAIL);

        if (redisUtil.exists(toEmail)) {
            redisUtil.delete(toEmail);
        }

        MimeMessage emailForm = createEmailForm(toEmail);


        mailSender.send(emailForm);

    }

    public void verifyEmailCode(String email, String code) {
        String codeFoundByEmail = redisUtil.getData(email);
        if (codeFoundByEmail == null) throw new CustomException(ErrorCode.INVALID_EMAIL);

        if (codeFoundByEmail.equals(code)) {
            delete(email);
        } else {
            throw new CustomException(ErrorCode.INVALID_CODE);
        }



    }

    public void delete(String key) {
        redisUtil.delete(key);
    }


}
