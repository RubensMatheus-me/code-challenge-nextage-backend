package com.nextage.code.challenge.models;

import com.nextage.code.challenge.enums.EStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.time.LocalDateTime;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "titulo")
    @NotBlank(message = "O titulo é obrigatório")
    private String title;

    @Size(max = 255, message = "A descrição só pode ter 255 caracteres")
    @Column(name = "descricao")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EStatus status;

    @Column(name = "data_criacao")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = EStatus.PENDENTE;
        }
    }
}

