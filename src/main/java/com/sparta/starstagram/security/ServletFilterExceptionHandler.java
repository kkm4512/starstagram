package com.sparta.starstagram.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.starstagram.exception.BaseException;
import com.sparta.starstagram.model.BaseResponseDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/**
 * 일반적인 HttpRequest요청에 대한 예외처리 잡기 위해 생성
 */
@Slf4j
@RequiredArgsConstructor
@Order(1)
public class ServletFilterExceptionHandler extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain fc) throws ServletException, IOException {
        try {
            fc.doFilter(req, res);
        } catch (BaseException e) {
            res.setStatus(e.getResponse().getStatus());
            res.setContentType("application/json;charset=UTF-8");
            res.getWriter()
                    .write(objectMapper.writeValueAsString(new BaseResponseDto(e.getResponse())));
        }
    }
}
