package com.nextage.code.challenge.dto;

import lombok.Data;

@Data

public class ChangePasswordDTO {
    private String email;
    private String password;
    private String confirmedPassword;
    private String code;
}
