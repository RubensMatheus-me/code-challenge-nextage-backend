package com.nextage.codeChallenge.dto;

import lombok.Data;

@Data

public class ChangePasswordDTO {
    private String email;
    private String password;
    private String confirmPassword;
    private String code;
}
