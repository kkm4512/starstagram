package com.sparta.starstagram.controller;

import com.sparta.starstagram.constans.BaseResponseEnum;
import com.sparta.starstagram.exception.JwtTokenExceptionHandler;
import com.sparta.starstagram.exception.UserPasswordException;
import com.sparta.starstagram.model.UserDeleteRequest;
import com.sparta.starstagram.model.UserNewPasswordRequestDto;
import com.sparta.starstagram.model.UserRequestDto;
import com.sparta.starstagram.model.UserResponseDto;
import com.sparta.starstagram.security.UserDetailsImpl;
import com.sparta.starstagram.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
     */
    // 포스트맨 테스트 URL = http://localhost:8080/api/user/signup?email=test@naver.com&password=123&username=test
//    @PostMapping("/api/user/signup")
//    public void registerUser(@RequestBody @Valid UserRequestDto requestDto, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            Map<String, String> errors = new HashMap<>();
//
//            for (FieldError error : bindingResult.getFieldErrors()) {
//                errors.put(error.getField(), error.getDefaultMessage());
//            }
//
//            throw new UserPasswordException(BaseResponseEnum.USER_PASSWORD_FORMAT);
//        }
//        userService.registerUser(requestDto);
//    }
    @PostMapping("/api/user/signup")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body("회원가입 실패: " + errors.toString());
        }

        try {
            userService.registerUser(requestDto);
            return ResponseEntity.ok("회원가입이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    /**
     * 회원탈퇴
     * @param request
     * @return
     */
    @DeleteMapping("/api/user/unregister")
    public ResponseEntity<String> deleteUser(@RequestBody UserDeleteRequest request) {
        try {
            userService.deleteUser(request.getEmail(), request.getPassword());
            return ResponseEntity.ok("회원탈퇴가 완료 되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
