package com.sparta.starstagram.util;

import com.sparta.starstagram.constans.BaseResponseEnum;
import com.sparta.starstagram.entity.Post;
import com.sparta.starstagram.entity.User;
import com.sparta.starstagram.exception.HandleNotFoundException;
import com.sparta.starstagram.repository.PostRepository;
import com.sparta.starstagram.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UtilFind {
    private final UserRepository userRepository;
    private final PostRepository boardRepository;
    private final JwtUtil jwtUtil;

    /**
     * id로 사용자를 찾는 메서드
     *
     * @param id 찾을 사용자 ID
     * @return 조회된 사용자 반환
     * @throws HandleNotFoundException 사용자가 없을시 발생되는 예외
     * @author 김경민
     */
    public User userFindById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new HandleNotFoundException(BaseResponseEnum.USER_NOT_FOUND));
    }

    /**
     * 이름으로 유저 찾는 메서드
     *
     * @param username 유저를 찾을 이름
     * @return 조회돤 유저 반호
     * @throws HandleNotFoundException 사용자가 없을시 발생되는 예외
     * @author 김경민
     */
    public User userFindByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new HandleNotFoundException(BaseResponseEnum.USER_NOT_FOUND));
    }

    /**
     * 이름으로 유저 찾는 메서드
     *
     * @param username 유저를 찾을 이름
     * @return 조회돤 유저 반호
     * @throws HandleNotFoundException 사용자가 없을시 발생되는 예외
     * @author 김경민
     */
    public User userFindByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new HandleNotFoundException(BaseResponseEnum.USER_NOT_FOUND));
    }

    /**
     * 헤더에있는 Jwt 뽑아서, username으로 조회후 User 반환
     *
     * @param req 서블릿 리퀘스트
     * @return username으로 찾은 유저 반환
     */
    public User userFindByHeaderJwtUsername(HttpServletRequest req){
        String jwt = jwtUtil.resolveToken(req);
        Claims info = jwtUtil.getUserInfoFromToken(jwt);
        String username = info.getSubject();
        return userRepository.findByUsername(username).orElseThrow(() -> new HandleNotFoundException(BaseResponseEnum.USER_NOT_FOUND));
    }

    /**
     * id로 게시글 찾는 메서드
     *
     * @param id 찾을 게시글 ID
     * @return 조회된 게시글 반환
     * @throws HandleNotFoundException 게시글이 없을시 발생되는 예외
     * @author 김경민
     */
    public Post boardFindById(Long id){
        return boardRepository.findById(id).orElseThrow(() -> new HandleNotFoundException(BaseResponseEnum.USER_NOT_FOUND));
    }
}
