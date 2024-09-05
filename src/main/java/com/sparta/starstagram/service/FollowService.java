package com.sparta.starstagram.service;

import com.sparta.starstagram.constans.BaseResponseEnum;
import com.sparta.starstagram.entity.Follow;
import com.sparta.starstagram.entity.User;
import com.sparta.starstagram.exception.HandleFollowException;
import com.sparta.starstagram.exception.HandleNotFoundException;
import com.sparta.starstagram.repository.FollowRepository;
import com.sparta.starstagram.repository.UserRepository;
import com.sparta.starstagram.util.UtilFind;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final UtilFind utilFind;

    /**
     * 로그인한 사용자가 다른 사용자를 팔로우하는 로직
     *
     * @param followId  팔로우 할 사용자의 Id(=유저 Id와 같다)
     * @param loginUser 현재 로그인한 사용자
     * @return 팔로우 성공 유무가 담긴 응답 반환
     */
    @Transactional
    public BaseResponseEnum addFollow(Long followId, User loginUser) {
        try {
            User follow = utilFind.findFollowById(followId);

            // 이미 팔로우되어있는지 확인하는 로직
            Follow existingFollow = followRepository.findByUserAndFollow(loginUser, follow);
            if (existingFollow != null) {
                throw new HandleFollowException(BaseResponseEnum.ALREADY_FOLLOW);
            }

            // 새로운 팔로우 관계 생성
            Follow newFollow = new Follow(loginUser, follow);
            followRepository.save(newFollow);

            return BaseResponseEnum.FOLLOW_SUCCESS;  // 성공 시
        } catch (Exception e) {
            throw new HandleFollowException(BaseResponseEnum.FOLLOW_FAIL);  // 실패 시
        }
    }

    /**
     * 로그인한 사용자가 기존에 팔로우했던 사용자를 팔로우 삭제하는 로직
     *
     * @param followId 팔로우 할 친구의 ID(=유저 ID와 같다)
     * @param loginUser 현재 로그인한 사용자
     * @throws HandleNotFoundException 팔로우 삭제하려는 사용자가 존재하지 않을 경우 예외 처리
     */
    @Transactional
    public BaseResponseEnum removeFollow(Long followId, User loginUser) {
        try {
            // 팔로우 삭제하려는 사용자 찾기
            User follow = utilFind.findFollowById(followId);

            // 팔로우 관계가 존재하는지 확인
            Follow followEntity = followRepository.findByUserAndFollow(loginUser, follow);
            if (followEntity == null) {
                throw new HandleNotFoundException(BaseResponseEnum.FOLLOW_FRIEND_NOT_FOUND);
            }
            // 팔로우 삭제
            followRepository.delete(followEntity);
            return BaseResponseEnum.FOLLOW_DELETE_SUCCESS;
        } catch (Exception e) {
            return BaseResponseEnum.FOLlOW_DELETE_FAIL;
        }

    }

}

