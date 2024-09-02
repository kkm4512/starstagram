package com.sparta.starstagram.util;

import com.sparta.starstagram.constans.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import io.jsonwebtoken.security.Keys;
import org.springframework.util.StringUtils;

import java.util.Base64;
import java.util.Date;
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

    @Value("${jwt.secret.key}")
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

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username) //사용자 식별자 값(ID)
                        .claim(AUTHORIZATION_KEY, role) //사용자 권한
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) //만료 시간
                        .setIssuedAt(date) //발급일
                        .signWith(key, signatureAlgorithm) //암호화 알고리즘
                        .compact();
    }

    /**
     * JWT 헤더에 저장
     */
    public void addJwtToCookie(String token, HttpServletResponse res) {
        try {
            token = URLEncoder.encode(token, "utf-8").replace("\\+", "%20");

            res.addHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + token);

            //Cookie cookie = new Cookie(AUTHORIZATION_KEY, token);
            //cookie.setPath("/");

            //res.addHeader("Auth", token);
        } catch (UnsupportedEncodingException e) {
            logger.severe(e.getMessage());
        }
    }

    /**
     * 헤더에서 JWT 토큰 추출
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7); //Bearer 부분을 제외한 JWT 반환
        }
        return null; //유효한 JWT가 없으면 null을 반환

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
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            logger.severe("Invalid JWT signature, 유효하지 않은 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            logger.severe("Expired JWT token, 만료 된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            logger.severe("Unsupported JWT token, 지원되지 않은 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            logger.severe("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }

        return false;
    }

}
