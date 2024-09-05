package com.sparta.starstagram.exception;

import com.sparta.starstagram.constans.BaseResponseEnum;
import lombok.Getter;


@Getter
public class HandlePasswordValidateException extends BaseException{
    public HandlePasswordValidateException(BaseResponseEnum response) {
        super(response);
    }
}
