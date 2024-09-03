package com.sparta.starstagram.controller;

import com.sparta.starstagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/signup")
    public void registerUser(@RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String username) {
        userService.registerUser(email, password, username);
    }
}
