package com.sparta.starstagram.dto;

import lombok.Getter;
import com.sparta.starstagram.entity.User;

@Getter
public class UserResponseDto {
    private Long id;
    private String nickname;
    private String email;


    public UserResponseDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }
}
