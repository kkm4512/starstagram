package com.sparta.starstagram.exception;

import com.sparta.starstagram.constans.BaseResponseEnum;

/**
 * Jwt 관련 예외 모음
 */
public class JwtTokenExceptionHandler extends BaseException{
    public JwtTokenExceptionHandler(BaseResponseEnum response) {
        super(response);
    }
}
