package com.sparta.starstagram.service;

import com.sparta.starstagram.constans.UserRoleEnum;
import com.sparta.starstagram.dto.UserRequestDto;
import com.sparta.starstagram.dto.UserResponseDto;
import com.sparta.starstagram.entity.User;
import com.sparta.starstagram.repository.UserRepository;
import com.sparta.starstagram.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private JwtUtil jwtUtil;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordEncoder passwordEncoder;


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


    @Transactional
    public UserResponseDto getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return new UserResponseDto(user);
    }


    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        // ID를 통해 사용자 정보 조회
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

        //입력된 현재 비밀번호 올바른지 확인
        if (!passwordEncoder.matches(userRequestDto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        //새 비밀번호와 현재 비밀번호가 동일하지 않은지 확인
        if (passwordEncoder.matches(userRequestDto.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("New password cannot be the same as the current password");
        }

        //새 비밀번호 형식이 올바른지 확인
        if (!isValidPasswordFormat(userRequestDto.getNewPassword())) {
            throw new IllegalArgumentException("New password format is invalid");
        }

        //새로운 비밀번호 인코딩 하여 저장
        user.setPassword(passwordEncoder.encode(userRequestDto.getNewPassword()));

        return new UserResponseDto(user);
    }

    private boolean isValidPasswordFormat(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[!@#$%^&*()].*");
    }

}
