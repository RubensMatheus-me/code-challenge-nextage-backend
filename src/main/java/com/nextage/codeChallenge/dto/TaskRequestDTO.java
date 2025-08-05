package com.nextage.codeChallenge.dto;

import com.nextage.codeChallenge.enums.EStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskRequestDTO {
    private Long id;
    @NotBlank(message = "O titulo da tarefa é obrigatório")
    private String title;
    private String description;
    private EStatus status;
}
