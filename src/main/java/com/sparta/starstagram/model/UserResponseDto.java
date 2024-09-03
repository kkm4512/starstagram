package com.sparta.starstagram.model;

import lombok.Getter;
import com.sparta.starstagram.entity.User;

@Getter
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;


    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
