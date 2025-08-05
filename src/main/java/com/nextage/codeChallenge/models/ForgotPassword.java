package com.nextage.codeChallenge.models;

import jakarta.persistence.*;

import java.util.Date;
@Entity
@Table(name = "forgot_password")
public class ForgotPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "codigo", nullable = false)
    private String code;

    @Column(name = "tempo_expiracao", nullable = false)
    private Date expirationTime;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}
