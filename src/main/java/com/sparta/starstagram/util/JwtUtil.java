package com.sparta.starstagram.util;

import com.sparta.starstagram.constans.BaseResponseEnum;
import com.sparta.starstagram.constans.UserRoleEnum;
import com.sparta.starstagram.exception.JwtTokenExceptionHandler;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Component
public class JwtUtil {

    /**
     * JWT 생성
     */
    //Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";

    //사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "Auth";

    //Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";

    //토큰 만료시간
    private final long TOKEN_TIME = 60 * 60 * 1000; //60분

    @Value("${jwt.secret_key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    //로그 설정
    public static final Logger logger = Logger.getLogger("JWT");

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    /**
     * 토큰 생성
     */
    public String createToken(String username, UserRoleEnum role) {
        Date date = new Date();

        return BEARER_PREFIX + Jwts.builder()
                .setSubject(username)
                .claim(AUTHORIZATION_KEY, role.getAuthority())
                .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                .setIssuedAt(date)
                .signWith(key)
                .compact();
    }

    /**
     * JWT 헤더에 저장
     */
    public void addJwtToHeader(String token, HttpServletResponse res) {
        res.addHeader(AUTHORIZATION_HEADER, token);
    }

    /**
     * 헤더에서 JWT 토큰 추출
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * JWT에서 사용자 정보 가져오기
     */
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /**
     * 토큰 검증
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            logger.severe("Invalid JWT signature, 유효하지 않은 JWT 서명 입니다.");
            throw new JwtTokenExceptionHandler(BaseResponseEnum.JWT_NOT_VALID);
        } catch (ExpiredJwtException e) {
            logger.severe("Expired JWT token, 만료 된 JWT token 입니다.");
            throw new JwtTokenExceptionHandler(BaseResponseEnum.JWT_EXPIRED);
        } catch (UnsupportedJwtException e) {
            logger.severe("Unsupported JWT token, 지원되지 않은 JWT 토큰 입니다.");
            throw new JwtTokenExceptionHandler(BaseResponseEnum.JWT_UNSUPPORTED);
        } catch (IllegalArgumentException e) {
            logger.severe("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            throw new JwtTokenExceptionHandler(BaseResponseEnum.JWT_NOT_FOUND);
        }
        return true;
    }

    public Collection<? extends GrantedAuthority> getUserAuthorityFromToken(String token) {
        // 여기에 권한 추출 로직을 구현합니다. (예: ROLE_USER 등)
        return List.of(new SimpleGrantedAuthority((String) getUserInfoFromToken(token).get(AUTHORIZATION_KEY)));
    }

}
