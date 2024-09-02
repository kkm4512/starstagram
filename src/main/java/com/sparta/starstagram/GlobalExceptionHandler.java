package com.sparta.starstagram;

import com.sparta.starstagram.exception.BaseException;
import com.sparta.starstagram.model.BaseResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponseDto> baseException(BaseException e) {
        BaseResponseDto rs = new BaseResponseDto(e.getResponse());
        // TODO 여기 나중에 Status 부분에 숫자가 표기되게 하기
        return new ResponseEntity<>(rs,rs.getStatus());
    }
}
