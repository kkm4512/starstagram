package com.sparta.starstagram.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TimeStampDto {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
