package com.nextage.code.challenge.services;

import com.nextage.code.challenge.models.User;
import com.nextage.code.challenge.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void verifyEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow( () -> new RuntimeException("Usuário não encontrado"));

        if(user.getActive()) {
            throw new IllegalStateException("Email já verificado");
        }

        user.setActive(true);
        userRepository.save(user);
    }
}
