package com.nextage.codeChallenge.controllers;

import com.nextage.codeChallenge.dto.EmailVerificationDTO;
import com.nextage.codeChallenge.repositories.UserRepository;
import com.nextage.codeChallenge.repositories.VerifyEmailRepository;
import com.nextage.codeChallenge.services.EmailService;
import com.nextage.codeChallenge.services.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/email")
public class EmailController {

    private final EmailService emailService;
    private final UserService userService;
    private final VerifyEmailRepository verifyEmailRepository;
    private final UserRepository userRepository;

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        try {
            emailService.verifyEmailToken(token);
            return ResponseEntity.ok("Conta ativada com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<String> resendVerification(@RequestBody String email) {
        emailService.sendVerificationEmail(email);
        return ResponseEntity.ok("Email reenviado com sucesso.");
    }

}
