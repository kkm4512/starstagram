package com.sparta.starstagram.security;

import com.sparta.starstagram.constans.BaseResponseEnum;
import com.sparta.starstagram.exception.JwtTokenExceptionHandler;
import com.sparta.starstagram.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Order(0)
@Slf4j(topic = "JwtAuthorizationFilter")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain fc) throws ServletException, IOException {
        log.info("JWT 인가 로직 시작");
        try {
            String jwt = jwtUtil.resolveToken(req);
            if (StringUtils.hasText(jwt)) {
                jwtUtil.validateToken(jwt);
                Claims claims = jwtUtil.getUserInfoFromToken(jwt);
                setAuthentication(claims.getSubject());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            req.setAttribute("exception", e);
            throw e;
        }
        fc.doFilter(req, res);
        log.info("JWT 인가 로직 끝");
    }

    // 인증 처리
    private void setAuthentication(String username) {
        try {
            log.info("SecurityContextHolder에 유저 정보 넣기");
            SecurityContext sc = SecurityContextHolder.createEmptyContext();
            Authentication auth = createAuthentication(username);
            sc.setAuthentication(auth);
            SecurityContextHolder.setContext(sc);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new JwtTokenExceptionHandler(BaseResponseEnum.JWT_NOT_VALID);
        }
        log.info("SecurityContextHolder에 유저 정보 넣어주기 끝");
    }

    //인증객체 생성
    private Authentication createAuthentication(String username) {
        try {
            log.info("UsernamePasswordAuthenticationToken 에 데이터 넘겨주기");
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            log.info("userDetails.getUsername() : " + userDetails.getUsername());
            log.info("userDetails.getPassword() : " + userDetails.getPassword());
            return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new JwtTokenExceptionHandler(BaseResponseEnum.JWT_NOT_VALID);
        }
    }
}
