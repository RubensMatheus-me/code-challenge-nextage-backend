package com.nextage.codeChallenge.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Date createdAt;
}
