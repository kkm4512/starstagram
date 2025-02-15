package com.sparta.starstagram.model.user;

import lombok.Getter;
import com.sparta.starstagram.entity.User;

@Getter
public class UserResponseDto {
    private String username;
    private String email;


    public UserResponseDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
