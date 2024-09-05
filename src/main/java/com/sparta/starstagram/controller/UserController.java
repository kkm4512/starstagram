package com.sparta.starstagram.controller;

import com.sparta.starstagram.constans.BaseResponseEnum;
import com.sparta.starstagram.entity.User;
import com.sparta.starstagram.model.BaseResponseDto;
import com.sparta.starstagram.model.user.UserDeleteRequest;
import com.sparta.starstagram.model.user.UserNewPasswordRequestDto;
import com.sparta.starstagram.model.user.UserRequestDto;
import com.sparta.starstagram.model.user.UserResponseDto;
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
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    /**
     * 프로필 조회 API
     *
     * @param id 조회할 유저의 ID
     *
     * @return 유저 정보를 담은 ResponseEntity
     *
     * @author 이태건
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    /**
     * 프로필 수정 기능 API
     *
     * @return 수정된 유저 정보를 담은 ResponseEntity
     *
     * @author 이태건
     */
   @PutMapping
   public ResponseEntity<BaseResponseDto> updateUser(@RequestBody UserNewPasswordRequestDto userRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User loginUser = userDetails.getUser();
        BaseResponseEnum responseEnum = userService.updateUser(userRequestDto, loginUser);
        return UtilResponse.getResponseEntity(responseEnum);
   }

    /**
     * 회원가입
     *
     * @param requestDto 사용자 정보(아이디, 패스워드, 닉네임)
     *
     * @author tiyu
     */
    @PostMapping("/signup")
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
    @DeleteMapping("/unregister")
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
