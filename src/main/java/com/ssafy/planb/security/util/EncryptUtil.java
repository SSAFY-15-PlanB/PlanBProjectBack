package com.ssafy.planb.security.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@RequiredArgsConstructor
public class EncryptUtil {

    private final PasswordEncoder passwordEncoder;
    private final AesBytesEncryptor aesBytesEncryptor;

    public String aesEncrypt(String plainText) {
        byte[] encrypted = aesBytesEncryptor.encrypt(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String aesDecrypt(String base64CipherText) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64CipherText);
        byte[] decrypted = aesBytesEncryptor.decrypt(decodedBytes);
        return new String(decrypted);
    }

    public String encryptPwd(String pwd) {
        return passwordEncoder.encode(pwd);
    }
}
