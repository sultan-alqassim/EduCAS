package com.example.eduCAS.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
}
