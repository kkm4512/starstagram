package com.sparta.starstagram.service;

import com.sparta.starstagram.entity.User;
import com.sparta.starstagram.repository.UserRepository;
import com.sparta.starstagram.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private JwtUtil jwtUtil;

    public void registerUser(String email, String password, String nickname) {
        // 이메일 중복 확인
        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email already exists");
        }

        // 새 사용자 생성 및 저장
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setNickname(nickname);

        userRepository.save(newUser);
    }

}
