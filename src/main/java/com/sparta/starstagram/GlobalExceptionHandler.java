package com.sparta.starstagram;

import com.sparta.starstagram.constans.BaseResponseEnum;
import com.sparta.starstagram.exception.BaseException;
import com.sparta.starstagram.model.BaseResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponseDto> baseException(BaseException e) {
        BaseResponseEnum status = e.getResponse();
        return ResponseEntity.status(status.getStatus()).body(new BaseResponseDto(status));
    }
}
