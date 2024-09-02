package com.sparta.starstagram.model;

import com.sparta.starstagram.constans.BaseResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class BaseResponseDto {
    private boolean success;
    private HttpStatus status;
    private String message;

    //BaseResponseEnum 의 상태를 클라이언트로 던져주기위한 생성자
    public BaseResponseDto(BaseResponseEnum baseResponseEnum) {
        this.success = baseResponseEnum.isSuccess();
        this.status = baseResponseEnum.getStatus();
        this.message = baseResponseEnum.getMessage();
    }
}
