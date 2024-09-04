package com.sparta.starstagram.controller;

import com.sparta.starstagram.constans.BaseResponseEnum;
import com.sparta.starstagram.entity.User;
import com.sparta.starstagram.model.BaseResponseDto;
import com.sparta.starstagram.model.friend.FriendSaveResponseDto;
import com.sparta.starstagram.security.UserDetailsImpl;
import com.sparta.starstagram.service.FriendService;
import com.sparta.starstagram.util.UtilResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/otherUsers/{friendId}")
public class FriendController {

    private final FriendService friendService;


    /**
     * 로그인한 사용자가 친구를 추가하는 기능을 수행하는 메서드
     *
     * @param friendId 추가하려는 친구의 아이디(=유저 아이디랑 같음)
     * @param userDetails 현재 로그인된 사용자의 정보
     * @return FriendSaveResponseDto 현재 로그인한 사용자를 인자로 받아서 친구를 저장한 값을 FriendSaveResponseDto 객체로 반환
     * @throws ???중복된 친구는 친구 추가할 수 없다를 예외 처리하고 싶은데...? 예외를 배웠어야 말이징..
     *
     * @author 황윤서
     */
    @PostMapping
    public ResponseEntity<BaseResponseDto> saveFriend(@PathVariable Long friendId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User loginUser = userDetails.getUser();

        FriendSaveResponseDto friendSaveResponseDto = friendService.saveFriend(friendId, loginUser);

        return UtilResponse.getResponseEntity(BaseResponseEnum.FOLLOW_SUCCESS);
    }

    /**
     * 친구를 삭제하는 메서드
     *
     * @param friendId 삭제하려는 친구의 아이디
     *
     * @author 황윤서
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteFriend(@PathVariable("friendId") Long friendId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        friendService.deleteFriend(friendId, userDetails.getUser());
        return ResponseEntity.ok().build();
    }
}
