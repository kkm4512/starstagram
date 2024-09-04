package com.sparta.starstagram.util;

import com.sparta.starstagram.constans.BaseResponseEnum;
import com.sparta.starstagram.entity.User;
import com.sparta.starstagram.exception.UserMismatchException;
import org.springframework.stereotype.Component;

@Component
public class UtilValidator {
    /**
     * 로그인한 유저가 처리하려는 게시글의 작성자(유저)와 동일한지 확인 메서드
     *
     * @param loginUser 로그인한 유저
     * @param boardUser 게시글을 작성한 유저
     * @author 김경민
     * @throws UserMismatchException 유저가 다른 게시글을 수정,삭제 할때 발생되는 예외
     */
    public static void isSameUser(User loginUser,User boardUser){
        if (!loginUser.getUsername().equals(boardUser.getUsername())) throw new UserMismatchException(BaseResponseEnum.USER_MISMATCH_BOARD);
    }
}
