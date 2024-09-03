package com.sparta.starstagram.controller;

import com.sparta.starstagram.model.UserRequestDto;
import com.sparta.starstagram.model.UserResponseDto;
import com.sparta.starstagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/api/user/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PutMapping("/api/user/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(userService.updateUser(id, userRequestDto));
    }

    @PostMapping("api/user/signup")
    public void registerUser(@RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String username) {
        userService.registerUser(email, password, username);
    }

}
