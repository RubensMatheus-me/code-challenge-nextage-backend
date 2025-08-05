package com.nextage.codeChallenge.services;

import com.nextage.codeChallenge.dto.EmailVerificationDTO;
import com.nextage.codeChallenge.models.VerifyEmail;
import com.nextage.codeChallenge.repositories.UserRepository;
import com.nextage.codeChallenge.repositories.VerifyEmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final VerifyEmailRepository verifyEmailRepository;
    private final UserRepository userRepository;

    @Value("${app.base-email-url}")
    private String baseEmailUrl;

    @Async
    public void sendVerificationEmail(String email) {

        String token = UUID.randomUUID().toString();
        var user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuário não encontrado para o email informado"));

        VerifyEmail verifyEmail = new VerifyEmail();
        verifyEmail.setEmail(email);
        verifyEmail.setToken(token);
        verifyEmail.setCreatedAt(LocalDateTime.now());
        verifyEmail.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        verifyEmail.setUser(user);
        verifyEmailRepository.save(verifyEmail);

        String verificationUrl = baseEmailUrl + "/verify?token=" + token;

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject("Verifique sua conta");

            Context context = new Context();
            context.setVariable("name", email);
            context.setVariable("verificationUrl", verificationUrl);

            String htmlContent = templateEngine.process("verify-email", context);
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Falha ao enviar e-mail de verificação", e);
        }
    }

    public void sendForgotPasswordEmail(EmailVerificationDTO dto) {

    }

    public void verifyEmailToken(String token) {
        var verifyEmail = verifyEmailRepository.findByToken(token).orElseThrow( () -> new RuntimeException("Token inválido ou expirado"));

        if (verifyEmail.isUsed()) {
            throw new RuntimeException("Este link já foi utilizado");
        }

        if (verifyEmail.getExpiresAt().isBefore(LocalDateTime.now())) {
            verifyEmailRepository.delete(verifyEmail);
            throw new RuntimeException("Solicite um novo e-mail.");
        }

        var user = verifyEmail.getUser();
        user.setActive(true);
        userRepository.save(user);

        verifyEmail.setUsed(true);
        verifyEmailRepository.save(verifyEmail);

    }
}
