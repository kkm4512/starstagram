package com.sparta.starstagram.controller;

import com.sparta.starstagram.repository.UserRepository;
import com.sparta.starstagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
}
