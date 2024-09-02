package com.sparta.starstagram.service;

import com.sparta.starstagram.entity.Friend;
import com.sparta.starstagram.model.FriendSaveRequestDto;
import com.sparta.starstagram.model.FriendSaveResponseDto;
import com.sparta.starstagram.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    /**
     *
     * @param userId
     * @param friendSaveRequestDto
     * @return
     *
     * @author 황윤서
     */
    @Transactional
    public FriendSaveResponseDto saveFriends(Long userId, FriendSaveRequestDto friendSaveRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException("해당하는 유저가 없습니다."));

        Friend newFriend = new Friend(friendSaveRequestDto.getId(), friendSaveRequestDto.getFriendName());
        Friend saveFriend = friendRepository.save(newFriend);
        return new FriendSaveResponseDto(saveFriend.getId(), saveFriend.getFriendName(), new UserDto(???));
    }

    /**
     *
     * @param friendId
     *
     * @author 황윤서
     */
    @Transactional
    public void deleteFriend(Long friendId) {
        if (!friendRepository.existsById(friendId)) {
            throw new NullPointerException("삭제하려는 친구가 없습니다.");
        }

        friendRepository.deleteById(friendId);
    }

}
