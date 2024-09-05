package com.sparta.starstagram.model.follow;

import com.sparta.starstagram.model.user.UserDto;
import lombok.Getter;

@Getter
public class FollowSaveResponseDto {

    private final Long id;
    private final String followName;
    private final UserDto user;

    public FollowSaveResponseDto(Long id, String followName, UserDto user) {
        this.id = id;
        this.followName = followName;
        this.user = user;
    }
}
