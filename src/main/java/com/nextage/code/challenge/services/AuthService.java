package com.nextage.code.challenge.services;

import com.nextage.code.challenge.config.auth.JwtUtil;
import com.nextage.code.challenge.dto.AuthResponseDTO;
import com.nextage.code.challenge.dto.LoginRequestDTO;
import com.nextage.code.challenge.dto.RegisterRequestDTO;
import com.nextage.code.challenge.models.User;
import com.nextage.code.challenge.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${app.base-verification-email-url}")
    private String baseVerificationEmailUrl;

    public AuthResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        if(user.getActive() == null || !user.getActive()) {
            throw new RuntimeException("Conta não ativada. Verifique seu email.");
        }

        String token = jwtUtil.generateToken(user);

        return AuthResponseDTO.builder()
                .token(token)
                .message("Login realizado com sucesso")
                .build();

    }

    public AuthResponseDTO register(RegisterRequestDTO request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EntityExistsException("Email já registrado");
        }

        var user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActive(false);

        if(!request.getPassword().equals(request.getConfirmedPassword())) {
            throw new IllegalArgumentException("Senhas não conferem");
        }

        userRepository.save(user);

        emailService.sendVerificationEmail(user.getEmail());

        return AuthResponseDTO.builder()
                .message("Conta criada. Verifique seu email para ativar a conta.")
                .build();
    }
}
