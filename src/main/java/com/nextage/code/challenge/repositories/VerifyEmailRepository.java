package com.nextage.code.challenge.repositories;

import com.nextage.code.challenge.models.VerifyEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface VerifyEmailRepository extends JpaRepository<VerifyEmail, Long> {
    Optional<VerifyEmail> findByToken(String token);
}
