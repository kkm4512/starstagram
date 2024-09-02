package com.sparta.starstagram.dto;

import lombok.Getter;

@Getter
public class UserDto {

    private final Long id;
    private final String email;
    private final String ninkname;

    public UserDto(Long id, String email, String ninkname) {
        this.id = id;
        this.email = email;
        this.ninkname = ninkname;
    }
}
