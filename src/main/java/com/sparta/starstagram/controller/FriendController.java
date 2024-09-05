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
     * 팔로우 API
     *
     * @author 황윤서
     */
    @PostMapping
    public ResponseEntity<BaseResponseDto> saveFriend(@PathVariable Long friendId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User loginUser = userDetails.getUser();

        BaseResponseEnum responseEnum = friendService.saveFriend(friendId, loginUser);

        return UtilResponse.getResponseEntity(responseEnum);
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
