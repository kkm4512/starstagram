package com.sparta.starstagram.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.starstagram.constans.BaseResponseEnum;
import com.sparta.starstagram.exception.HandleNotFoundException;
import com.sparta.starstagram.model.BaseResponseDto;
import com.sparta.starstagram.model.user.UserRequestLoginDto;
import com.sparta.starstagram.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "JwtAuthenticationFilter")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(JwtUtil jwtUtil,ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
        setFilterProcessesUrl("/api/user/login");
    }

    //사용자 인증 시도 메서드
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            UserRequestLoginDto reqDto = new ObjectMapper().readValue(req.getInputStream(), UserRequestLoginDto.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            reqDto.getUsername(),
                            reqDto.getPassword(),
                            null
                    )
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            req.setAttribute("exception", new HandleNotFoundException(BaseResponseEnum.USER_NOT_FOUND));
            throw new HandleNotFoundException(BaseResponseEnum.USER_NOT_FOUND);
        }
    }
    //로그인 성공
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl user = (UserDetailsImpl) authResult.getPrincipal();
        String jwt = jwtUtil.createToken(user.getUsername(),user.getUser().getRole());
        jwtUtil.addJwtToHeader(jwt, res);
        log.info("JWT 인증 로직 시도 끝");
        log.info("로그인 시도 끝");
        BaseResponseEnum responseEnum = BaseResponseEnum.USER_LOGIN_SUCCESS;
        res.setStatus(responseEnum.getStatus());
        res.setContentType("application/json;charset=UTF-8");
        res.getWriter()
                .write(objectMapper.writeValueAsString(new BaseResponseDto(responseEnum)));
    }

    //로그인 실패
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException failed) throws IOException, ServletException {
        log.error("로그인 실패로직 시작");
        res.setStatus(401);
    }
}
