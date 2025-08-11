package com.nextage.code.challenge.repositories;

import com.nextage.code.challenge.models.ForgotPassword;
import com.nextage.code.challenge.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword ,Long> {
    @Query("SELECT fp FROM ForgotPassword fp WHERE fp.code = ?1 AND fp.user = ?2")
    Optional<ForgotPassword> findByCodeAndUser(String code, User user);

    Optional<ForgotPassword> findByUserEmail(String email);

}
