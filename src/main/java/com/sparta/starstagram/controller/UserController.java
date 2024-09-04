package com.sparta.starstagram.controller;

import com.sparta.starstagram.constans.BaseResponseEnum;
import com.sparta.starstagram.entity.User;
import com.sparta.starstagram.model.*;
import com.sparta.starstagram.security.UserDetailsImpl;
import com.sparta.starstagram.service.UserService;
import com.sparta.starstagram.util.UtilResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/api/user/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PutMapping("/api/user/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserNewPasswordRequestDto userRequestDto) {
        return ResponseEntity.ok(userService.updateUser(id, userRequestDto));
    }

    /**
     * 회원가입
     *
     * @param requestDto 사용자 정보(아이디, 패스워드, 닉네임)
     *
     * @author tiyu
     */
    @PostMapping("/api/user/signup")
    public ResponseEntity<BaseResponseDto> registerUser(@RequestBody @Valid UserRequestDto requestDto, BindingResult bindingResult) {
        try {
            userService.registerUser(requestDto, bindingResult);
            return UtilResponse.getResponseEntity(BaseResponseEnum.USER_SAVE_SUCCESS);
        } catch (IllegalArgumentException e) {
            return UtilResponse.getResponseEntity(BaseResponseEnum.USER_SAVE_FAIL);
        }
    }


    /**
     * 회원탈퇴
     * @param userDetails 로그인한 유저 정보
     *
     * @author tiyu
     */
    @DeleteMapping("/api/user/unregister")
    public ResponseEntity<BaseResponseDto> deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody UserDeleteRequest request) {
        User user = userDetails.getUser();
        try {
            userService.deleteUser(user, request.getPassword());
            return UtilResponse.getResponseEntity(BaseResponseEnum.USER_DELETE_SUCCESS);
        } catch (IllegalArgumentException e) {
            return UtilResponse.getResponseEntity(BaseResponseEnum.USER_DELETE_FAIL);
        }
    }


}
