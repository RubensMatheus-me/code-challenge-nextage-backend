package com.nextage.codeChallenge.controllers;

import com.nextage.codeChallenge.dto.EmailDTO;
import com.nextage.codeChallenge.repositories.UserRepository;
import com.nextage.codeChallenge.repositories.VerifyEmailRepository;
import com.nextage.codeChallenge.services.EmailService;
import com.nextage.codeChallenge.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/email")
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        emailService.verifyEmailToken(token);
        return ResponseEntity.ok("Conta ativada com sucesso");
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<String> resendVerification(@RequestBody EmailDTO dto) {
        emailService.sendVerificationEmail(dto.getEmail());
        return ResponseEntity.ok("Email reenviado com sucesso.");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> sendForgotPassword(@RequestBody EmailDTO dto) {
            emailService.sendForgotPasswordEmail(dto.getEmail());
            return ResponseEntity.ok("Código de recuperação enviado com sucesso.");
    }

}
