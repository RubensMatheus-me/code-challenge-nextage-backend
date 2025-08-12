package com.nextage.code.challenge.dto;

import com.nextage.code.challenge.enums.EStatus;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private EStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime initiateTask;
    private LocalDateTime endTask;
}
