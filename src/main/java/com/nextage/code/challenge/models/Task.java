package com.nextage.code.challenge.models;

import com.nextage.code.challenge.enums.EStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;


import java.time.LocalDateTime;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "data_tarefa_criacao", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "data_tarefa_inicio")
    private LocalDateTime initiateTask;

    @Column(name = "data_tarefa_fim")
    private LocalDateTime endTask;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = EStatus.PENDENTE;
        }
    }
}

