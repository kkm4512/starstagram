package com.sparta.starstagram.exception;

import com.sparta.starstagram.constans.BaseResponseEnum;
import lombok.Getter;

/**
 * 회원가입 시 아이디, 이메일이 중복일 경우 예외 처리
 */
@Getter
public class UserJoinIdException extends BaseException{
    public UserJoinIdException(BaseResponseEnum response) {
        super(response);
    }
}
