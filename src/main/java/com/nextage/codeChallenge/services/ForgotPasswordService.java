package com.nextage.codeChallenge.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@RequiredArgsConstructor
@Service
public class ForgotPasswordService {

    private static final SecureRandom secureRandom = new SecureRandom();

    private final UserService userService;
    private final EmailService emailService;

    private String baseForgotPasswordEmailUrl;

    private String generateCode(int lenght) {
        StringBuilder code = new StringBuilder(lenght);

        for(int i = 0; i < lenght; i++) {
            code.append(secureRandom.nextInt(10));
        }
        return code.toString();
    }

}
