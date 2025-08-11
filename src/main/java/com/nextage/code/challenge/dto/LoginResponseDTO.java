package com.nextage.code.challenge.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private String token;
    private String type;
    private Long userId;
    private String username;
    private String email;
}
