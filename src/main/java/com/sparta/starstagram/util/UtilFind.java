package com.sparta.starstagram.util;

import com.sparta.starstagram.constans.BaseResponseEnum;
import com.sparta.starstagram.entity.Post;
import com.sparta.starstagram.entity.User;
import com.sparta.starstagram.exception.HandleNotFoundException;
import com.sparta.starstagram.repository.FriendRepository;
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
    private final PostRepository postRepository;
    private final FriendRepository friendRepository;
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
     * 친구로 추가하려는 유저가 있는지, 친구 찾기 메서드?
     *
     * @param friendId 친구로 추가할 친구의 Id
     * @return 찾은 User 객체 반환
     * @throws NullPointerException 해당하는 유저가 존재하지 않을 경우 예외 처리
     *
     * @author 황윤서
     */
    public User findFriendById(Long friendId) {
        return userRepository.findById(friendId).orElseThrow(() -> new NullPointerException("해당하는 유저가 없습니다."));
    }

    /**
     * 친구 관계가 존재하는지 확인하는 메서드
     *
     * @parm friendId 친구 Id
     * @return 친구가 존재하는지 여부 반환
     *
     * @author 황윤서
     */
    public boolean existsFriendById(Long friendId) {
        return friendRepository.existsById(friendId);
    }
}
