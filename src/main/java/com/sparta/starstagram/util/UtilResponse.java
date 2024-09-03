package com.sparta.starstagram.util;

import com.sparta.starstagram.constans.BaseResponseEnum;
import com.sparta.starstagram.model.BaseResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class UtilResponse {

    // ResponseEntity 형식으로 반환시켜주는 메서드
    public static ResponseEntity<BaseResponseDto> getResponseEntity(BaseResponseEnum baseResponseEnum) {
        final HttpHeaders headers = new HttpHeaders();

        // 상태
        int status =  baseResponseEnum.getStatus();

        // 헤더
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        // 응답 데이터
        BaseResponseDto responseData = new BaseResponseDto(baseResponseEnum);

        return new ResponseEntity<>( responseData, headers,  status );
    }
}
