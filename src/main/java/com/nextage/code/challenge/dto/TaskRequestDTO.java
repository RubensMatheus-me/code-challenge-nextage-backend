package com.nextage.code.challenge.dto;

import com.nextage.code.challenge.enums.EStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TaskRequestDTO {
    private Long id;
    @NotBlank(message = "O titulo da tarefa é obrigatório")
    private String title;
    private String description;
    private EStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime initiateTask;
    private LocalDateTime endTask;
}
