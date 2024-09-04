package com.sparta.starstagram.model.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestPostDto {
    private String title;
    private String detail;
}
