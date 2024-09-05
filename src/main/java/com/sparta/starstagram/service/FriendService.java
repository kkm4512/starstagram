package com.sparta.starstagram.service;

import com.sparta.starstagram.constans.BaseResponseEnum;
import com.sparta.starstagram.entity.Friend;
import com.sparta.starstagram.entity.User;
import com.sparta.starstagram.exception.HandleNotFoundException;
import com.sparta.starstagram.model.friend.FriendSaveResponseDto;
import com.sparta.starstagram.model.user.UserDto;
import com.sparta.starstagram.repository.FriendRepository;
import com.sparta.starstagram.repository.UserRepository;
import com.sparta.starstagram.util.UtilFind;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final UtilFind utilFind;

    /**
     * 로그인한 사용자가 다른 사용자를 팔로우하는 로직
     *
     * @param friendId  팔로우 할 사용자의 Id(=유저 Id와 같다)
     * @param loginUser 현재 로그인한 사용자
     * @return 팔로우 성공 유무가 담긴 응답 반환
     */
    @Transactional
    public BaseResponseEnum saveFriend(Long friendId, User loginUser) {
        try {
            User friend = utilFind.findFriendById(friendId);

            // 이미 친구로 등록되어있는지 확인하는 로직
            Friend existingFriend = friendRepository.findByUserAndFriend(loginUser, friend);
            if(existingFriend != null) {
                return BaseResponseEnum.ALREADY_FRIEND;
            }

            // 새로운 친구 관계 추가
            Friend newFriend = new Friend(loginUser, friend);
            friendRepository.save(newFriend);

            return BaseResponseEnum.FOLLOW_SUCCESS;  // 성공 시
        } catch (Exception e) {
            return BaseResponseEnum.FOLLOW_FAIL;  // 실패 시
        }
    }

    /**
     * 친구를 삭제하는 메서드
     *
     * @param friendId 삭제할 친구의 ID(=유저 ID와 같다)
     * @throws NullPointerException 삭제하려는 친구가 존재하지 않을 경우 예외 처리
     * @author 황윤서
     */
    @Transactional
    public void deleteFriend(Long friendId, User loginUser) {
        User friend = utilFind.findFriendById(friendId);

        Friend friendEntity = friendRepository.findByUserAndFriend(loginUser, friend);
        if (friendEntity == null) {
            throw new HandleNotFoundException(BaseResponseEnum.FOLLOW_FRIEND_NOT_FOUND);
        }

        friendRepository.delete(friendEntity);
    }

}
