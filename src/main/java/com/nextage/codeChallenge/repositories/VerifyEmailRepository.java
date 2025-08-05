package com.nextage.codeChallenge.repositories;

import com.nextage.codeChallenge.models.VerifyEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface VerifyEmailRepository extends JpaRepository<VerifyEmail, Long> {
    Optional<VerifyEmail> findByToken(String token);
}
