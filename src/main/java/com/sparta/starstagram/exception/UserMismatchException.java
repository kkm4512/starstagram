package com.sparta.starstagram.exception;

import com.sparta.starstagram.constans.BaseResponseEnum;

/**
 * 요청한 사용자가, 서버에 저장된 데이터와 상이할때 발생되는 예외
 */
public class UserMismatchException extends BaseException {
    public UserMismatchException(BaseResponseEnum response) {
        super(response);
    }
}
