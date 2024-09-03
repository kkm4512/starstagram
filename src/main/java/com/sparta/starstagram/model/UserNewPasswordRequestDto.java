package com.sparta.starstagram.model;

import lombok.Getter;

@Getter
public class UserNewPasswordRequestDto {
    private String currentPassword;
    private String newPassword;
}
