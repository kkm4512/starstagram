package com.sparta.starstagram.service;

import com.sparta.starstagram.entity.Friend;
import com.sparta.starstagram.entity.User;
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
     * 로그인한 사용자가 친구를 추가하는 기능을 수행하는 메서드
     *
     * @param friendId 친구로 추가할 사용자의 Id(=유저 Id와 같다)
     * @param loginUser 현재 로그인한 사용자
     * @return FriendSaveResponseDto 친구 추가 후 저장된 친구 정보를 FriendSaveResponseDto 객체로 반환
     *
     * @author 황윤서
     */
    @Transactional
    public FriendSaveResponseDto saveFriend(Long friendId, User loginUser) {
        User friend = utilFind.findFriendById(friendId);

        Friend newFriend = new Friend(loginUser, friend);
        Friend saveFriend = friendRepository.save(newFriend);

        // UserDto를 사용하기 위해 만들었음(※ entity는 service에 올 수 없기 때문에)
        UserDto userDto = new UserDto(
                saveFriend.getUser().getId(),
                saveFriend.getUser().getEmail(),
                saveFriend.getUser().getUsername()
        );

        return new FriendSaveResponseDto(
                saveFriend.getUserFriendId(),
                saveFriend.getFriend().getUsername(),
                userDto);
    }

    /**
     * 친구를 삭제하는 메서드
     *
     * @param friendId 삭제할 친구의 ID(=유저 ID와 같다)
     *
     * @throws NullPointerException 삭제하려는 친구가 존재하지 않을 경우 예외 처리
     *
     * @author 황윤서
     */
    @Transactional
    public void deleteFriend(Long friendId, User loginUser) {
        User friend = utilFind.findFriendById(friendId);

        if (friend == null) {
            throw new NullPointerException("삭제하려는 친구가 없습니다.");
        }

        Friend friendEntity = friendRepository.findByUserAndFriend(loginUser, friend);

        if (friendEntity == null) {
            throw new NullPointerException("해당 친구 관계가 존재하지 않습니다.");
        }

        friendRepository.delete(friendEntity);
    }

}
