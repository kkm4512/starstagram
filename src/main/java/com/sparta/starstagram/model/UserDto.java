package com.sparta.starstagram.model;

import lombok.Getter;

@Getter
public class UserDto {

    private final Long id;
    private final String email;
    private final String username;

    public UserDto(Long id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }
}
