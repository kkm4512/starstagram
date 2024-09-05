package com.sparta.starstagram.util;

import com.sparta.starstagram.constans.BaseResponseEnum;
import com.sparta.starstagram.entity.Post;
import com.sparta.starstagram.entity.User;
import com.sparta.starstagram.exception.HandleNotFoundException;
import com.sparta.starstagram.repository.FollowRepository;
import com.sparta.starstagram.repository.PostRepository;
import com.sparta.starstagram.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UtilFind {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final FollowRepository followRepository;
    private final JwtUtil jwtUtil;

    /**
     * id로 사용자를 찾는 메서드
     *
     * @param id 찾을 사용자 ID
     * @return 조회된 사용자 반환
     * @throws HandleNotFoundException 사용자가 없을시 발생되는 예외
     * @author 김경민
     */
    public  User userFindById(Long id){
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
     * @param email 유저를 찾을 이메일
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
     * @author 김경민
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
    public Post postFindById(Long id){
        return postRepository.findById(id).orElseThrow(() -> new HandleNotFoundException(BaseResponseEnum.POST_NOT_FOUND));
    }

    /**
     * 중복된 이메일 체크
     *
     * @param email
     * @return
     * @author tiyu
     */
    public boolean userDuplicatedEmail(String email) {
        Optional<User> userByEmail = userRepository.findByEmail(email);
        if (userByEmail.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 중복된 닉네임 체크
     *
     * @param username
     * @return
     * @author tiyu
     */
    public boolean userDuplicatedUsername(String username) {
        Optional<User> userByUsername = userRepository.findByUsername(username);
        if (userByUsername.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 친구로 추가하려는 유저가 있는지, 친구 찾기 메서드?
     *
     * @param followId 팔로우 할 사용자의 Id
     * @return 찾은 User 객체 반환
     * @throws NullPointerException 해당하는 유저가 존재하지 않을 경우 예외 처리
     *
     * @author 황윤서
     */
    public User findFollowById(Long followId) {
        return userRepository.findById(followId).orElseThrow(() -> new HandleNotFoundException(BaseResponseEnum.FOLLOW_FRIEND_NOT_FOUND));
    }

    /**
     * 팔로우 관계가 존재하는지 확인하는 메서드
     *
     * @parm followId 사용자 Id
     * @return 팔로우 할 사용자가 존재하는지 여부 반환
     *
     * @author 황윤서
     */
    public boolean existsFollowById(Long followId) {
        return followRepository.existsById(followId);
    }
}
