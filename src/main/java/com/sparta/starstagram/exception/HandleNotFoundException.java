package com.sparta.starstagram.exception;

import com.sparta.starstagram.constans.BaseResponseEnum;
import lombok.Getter;

/**
 * 못찾을때 발생되는 예외처리
 */
@Getter
public class HandleNotFoundException extends BaseException{
    public HandleNotFoundException(BaseResponseEnum response) {
        super(response);
    }
}
