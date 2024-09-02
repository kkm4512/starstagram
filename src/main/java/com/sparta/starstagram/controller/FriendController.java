package com.sparta.starstagram.controller;

import com.sparta.starstagram.model.FriendSaveRequestDto;
import com.sparta.starstagram.model.FriendSaveResponseDto;
import com.sparta.starstagram.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;


    /**
     * 친구의 이름을 받아서 친구를 추가하는 메서드
     *
     * @param friendSaveRequestDto 추가하려는 친구의 이름
     * @return
     *
     * @author 황윤서
     */
    @PostMapping("/api/otherUsers/{friendId}")
    public ResponseEntity<FriendSaveResponseDto> saveFriend(@RequestBody FriendSaveRequestDto friendSaveRequestDto) {
        return ResponseEntity.ok(friendService.saveFriend(friendSaveRequestDto));
    }

    /**
     * 친구의 아이디를 받아서 친구를 삭제하는 메서드
     *
     * @param friendId 삭제하려는 친구의 아이디
     * @return
     *
     * @author 황윤서
     */
    @DeleteMapping("/api/otherUsers/{friendId})")
    public void deleteFriend(@PathVariable("friendId") Long friendId) {
        friendService.deleteFriend(friendId);
    }
}
