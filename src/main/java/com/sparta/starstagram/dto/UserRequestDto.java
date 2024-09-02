package com.sparta.starstagram.dto;

import lombok.Getter;

@Getter
public class UserRequestDto {
    private String nickname;
    private String email;
    private String currentPassword;
    private String newPassword;
}
