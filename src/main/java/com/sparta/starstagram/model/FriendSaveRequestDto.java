package com.sparta.starstagram.model;

import lombok.Getter;

@Getter
public class FriendSaveRequestDto {

    private Long userId;
    private Long id;
    private String friendName;
}
