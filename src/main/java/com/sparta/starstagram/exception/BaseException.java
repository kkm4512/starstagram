package com.sparta.starstagram.exception;

import com.sparta.starstagram.constans.BaseResponseEnum;
import com.sparta.starstagram.model.BaseResponseDto;
import lombok.Getter;

/**
 * 모든 custom 예외 처리의 부모
 */
@Getter
public class BaseException extends RuntimeException {
    BaseResponseEnum response;
    public BaseException(BaseResponseEnum response) {
        this.response = response;
    }
}
