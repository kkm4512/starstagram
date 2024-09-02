package com.sparta.starstagram.util;

import com.sparta.starstagram.constans.BaseResponseEnum;
import com.sparta.starstagram.entity.Board;
import com.sparta.starstagram.exception.HandleNotFoundException;
import com.sparta.starstagram.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UtilFind {
//    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    /**
     * id로 사용자를 찾는 메서드
     *
     * @param id 찾을 사용자 ID
     * @return 조회된 사용자 반환
     * @throws HandleNotFoundException 사용자가 없을시 발생되는 예외
     * @author 김경민
     */
//    public User userFindById(long id){
//        return userRepository.findById(id).orElseThrow(() -> new HandleNotFoundException(BaseResponseEnum.USER_NOT_FOUND));
//    }

    /**
     * id로 게시글 찾는 메서드
     * @param id 찾을 게시글 ID
     * @return 조회된 게시글 반환
     * @throws HandleNotFoundException 게시글이 없을시 발생되는 예외
     * @author 김경민
     */
    public Board boardFindById(long id){
        return boardRepository.findById(id).orElseThrow(() -> new HandleNotFoundException(BaseResponseEnum.USER_NOT_FOUND));
    }
}
