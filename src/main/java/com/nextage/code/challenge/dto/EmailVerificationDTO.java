package com.nextage.code.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EmailVerificationDTO {
    private String to;
    private String name;
    private String verificationUrl;
}
