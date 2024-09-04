package com.sparta.starstagram.model.friend;

import com.sparta.starstagram.model.UserDto;
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
