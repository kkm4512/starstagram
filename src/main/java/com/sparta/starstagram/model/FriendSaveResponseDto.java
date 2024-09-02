package com.sparta.starstagram.model;

import com.sparta.starstagram.dto.UserDto;
import lombok.Getter;

@Getter
public class FriendSaveResponseDto {

    private final Long id;
    private final String friendName;
    private final UserDto user;

    public FriendSaveResponseDto(Long id, String friendName, UserDto user) {
        this.id = id;
        this.friendName = friendName;
        this.user = user;
    }
}
