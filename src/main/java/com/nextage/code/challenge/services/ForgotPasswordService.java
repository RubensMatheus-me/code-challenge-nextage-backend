package com.nextage.code.challenge.services;


import com.nextage.code.challenge.dto.ChangePasswordDTO;
import com.nextage.code.challenge.repositories.ForgotPasswordRepository;
import com.nextage.code.challenge.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ForgotPasswordService {

    private static final SecureRandom secureRandom = new SecureRandom();

    private final UserRepository userRepository;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final PasswordEncoder passwordEncoder;

    protected String generateCode() {
        StringBuilder code = new StringBuilder(6);

        for(int i = 0; i < 6; i++) {
            code.append(secureRandom.nextInt(10));
        }
        return code.toString();
    }

    public String changePassword(ChangePasswordDTO dto) {
        var user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new RuntimeException("Usuário não encontrado pelo email"));

        var fp = forgotPasswordRepository.findByCodeAndUser(dto.getCode(), user).orElseThrow( () -> new RuntimeException("Código inválido"));

        if (fp.getExpirationTime().isBefore(LocalDateTime.now())) {
            forgotPasswordRepository.delete(fp);
            throw new RuntimeException("Código expirado, solicite outro.");
        }

        if(!dto.getPassword().equals(dto.getConfirmedPassword())) {
            throw new RuntimeException("As senhas não coincidem");
        }

        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);
        forgotPasswordRepository.delete(fp);

        return "Senha alterada com sucesso!";

    }

}
