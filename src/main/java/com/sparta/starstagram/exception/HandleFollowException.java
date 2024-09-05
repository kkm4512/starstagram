package com.sparta.starstagram.exception;

import com.sparta.starstagram.constans.BaseResponseEnum;
import lombok.Getter;

/**
 * 팔로우 관련 예외 처리
 */
@Getter
public class HandleFollowException extends BaseException{
    public HandleFollowException(BaseResponseEnum response) {
        super(response);
    }
}
