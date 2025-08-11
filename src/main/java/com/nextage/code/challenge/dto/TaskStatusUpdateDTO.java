package com.nextage.code.challenge.dto;

import com.nextage.code.challenge.enums.EStatus;
import lombok.Data;

@Data
public class TaskStatusUpdateDTO {
    private EStatus status;
}
