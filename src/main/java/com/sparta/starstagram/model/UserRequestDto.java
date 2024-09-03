package com.sparta.starstagram.model;

import lombok.Getter;

@Getter
public class UserRequestDto {
    private String username;
    private String email;
    private String currentPassword;
    private String newPassword;
}
