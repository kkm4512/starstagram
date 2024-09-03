package com.sparta.starstagram.controller;

import com.sparta.starstagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public void registerUser(@RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String nickname) {
        userService.registerUser(email, password, nickname);
    }
}
