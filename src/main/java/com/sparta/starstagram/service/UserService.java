package com.sparta.starstagram.service;

import com.sparta.starstagram.constans.UserRoleEnum;
import com.sparta.starstagram.entity.User;
import com.sparta.starstagram.repository.UserRepository;
import com.sparta.starstagram.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private JwtUtil jwtUtil;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public String registerUser(String email, String nickname, String password) {
        //비밀번호 암호화
        String encodedPassword = bCryptPasswordEncoder.encode(password);

        //사용자 객체 생성 및 저장
        User newUser = new User(email, nickname, encodedPassword);
        userRepository.save(newUser);

        //JWT 생성
        String token = jwtUtil.createToken(email, UserRoleEnum.USER);

        return token;
    }

}
