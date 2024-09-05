package com.sparta.starstagram.controller;

import com.sparta.starstagram.constans.BaseResponseEnum;
import com.sparta.starstagram.entity.User;
import com.sparta.starstagram.model.BaseResponseDto;
import com.sparta.starstagram.security.UserDetailsImpl;
import com.sparta.starstagram.service.FollowService;
import com.sparta.starstagram.util.UtilResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/otherUsers/{followId}")
public class FollowController {

    private final FollowService followService;


    /**
     * 팔로우 추가 API
     *
     * @author 황윤서
     */
    @PostMapping
    public ResponseEntity<BaseResponseDto> addFollow(@PathVariable Long followId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User loginUser = userDetails.getUser();

        BaseResponseEnum responseEnum = followService.addFollow(followId, loginUser);

        return UtilResponse.getResponseEntity(responseEnum);
    }

    /**
     * 팔로우 삭제 API
     *
     * @author 황윤서
     */
    @DeleteMapping
    public ResponseEntity<BaseResponseDto> removeFollow(@PathVariable("followId") Long followId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BaseResponseEnum responseEnum = followService.removeFollow(followId, userDetails.getUser());
        return UtilResponse.getResponseEntity(responseEnum);
    }
}
