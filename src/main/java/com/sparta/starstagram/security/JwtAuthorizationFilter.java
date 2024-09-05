package com.sparta.starstagram.security;

import com.sparta.starstagram.constans.BaseResponseEnum;
import com.sparta.starstagram.exception.BaseException;
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


/**
 * 인가처리 필터
 */
@Order(2)
@Slf4j(topic = "JwtAuthorizationFilter")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain fc) throws ServletException, IOException {
        log.info("JWT 인가 로직 시작");
        String reqURL = req.getRequestURI();
        try {
            // jwt가 있다면 -> 로그인처리가 됐다는뜻
            String jwt = jwtUtil.resolveToken(req);
            if (StringUtils.hasText(jwt)) {
                jwtUtil.validateToken(jwt);
                Claims claims = jwtUtil.getUserInfoFromToken(jwt);
                setAuthentication(claims.getSubject());
            } else {
                // 로그인,회원가입 경로가 아니고 jwt가 없다면
                if (!reqURL.equals("/api/user/signup") && !reqURL.equals("/api/user/login") && !StringUtils.hasText(jwt)) {
                    BaseException e = new JwtTokenExceptionHandler(BaseResponseEnum.JWT_NOT_FOUND);
                    throw e;
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage());
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
