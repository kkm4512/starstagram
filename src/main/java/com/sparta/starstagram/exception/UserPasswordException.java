package com.sparta.starstagram.exception;

import com.sparta.starstagram.constans.BaseResponseEnum;
import lombok.Getter;

/**
 * 비밀번호 조건이 안맞을 때 예외처리
 */
@Getter
public class UserPasswordException extends BaseException{
    public UserPasswordException(BaseResponseEnum response) {
        super(response);
    }
}
