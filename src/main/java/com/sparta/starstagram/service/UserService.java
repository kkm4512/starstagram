package com.sparta.starstagram.service;

import com.sparta.starstagram.constans.BaseResponseEnum;
import com.sparta.starstagram.entity.DeletedUser;
import com.sparta.starstagram.entity.User;
import com.sparta.starstagram.exception.UserJoinIdException;
import com.sparta.starstagram.model.UserNewPasswordRequestDto;
import com.sparta.starstagram.model.UserRequestDto;
import com.sparta.starstagram.model.UserResponseDto;
import com.sparta.starstagram.repository.DeletedUserRepository;
import com.sparta.starstagram.repository.UserRepository;
import com.sparta.starstagram.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final DeletedUserRepository deletedUserRepository;

    private JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(UserRequestDto requestDto) {
        Optional<User> userByEmail = userRepository.findByEmail(requestDto.getEmail());
        if (!userByEmail.isEmpty()) {
            throw new UserJoinIdException(BaseResponseEnum.USER_DUPLICATED);
        }

        Optional<User> userByUsername = userRepository.findByUsername(requestDto.getUsername());
        if (!userByUsername.isEmpty()) {
            throw new UserJoinIdException(BaseResponseEnum.USER_USERNAME_DUPLICATED);
        }

        //탈퇴한 이메일 체크
        if (deletedUserRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("탈퇴한 이메일 입니다. 재가입이 불가능합니다.");
        }

        // 새 사용자 생성 및 저장
        User newUser = new User();
        String hashedPassword = passwordEncoder.encode(requestDto.getPassword());
        newUser.setEmail(requestDto.getEmail());
        newUser.updatePassword(hashedPassword);
        newUser.setUsername(requestDto.getUsername());

        userRepository.save(newUser);
    }

    /**
     * 회원탈퇴
     * @param email
     * @param password
     */
    @Transactional
    public void deleteUser(String email, String password) {
        //사용자 조회
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        //비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        //삭제된 사용자인지 확인
        if (deletedUserRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 탈퇴한 계정입니다.");
        }

        //사용자 삭제
        userRepository.delete(user);

        //DeletedUser 엔티티에 추가
        DeletedUser deletedUser = new DeletedUser(email, LocalDateTime.now());
        deletedUserRepository.save(deletedUser);
    }




    @Transactional(readOnly = true)
    public UserResponseDto getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return new UserResponseDto(user);
    }



    @Transactional
    public UserResponseDto updateUser(Long id, UserNewPasswordRequestDto userRequestDto) {
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
//        user.setPassword(passwordEncoder.encode(userRequestDto.getNewPassword()));
//
//        return new UserResponseDto(user);

        //새로운 비밀번호 인코딩 하여 저장
        String encodedPassword = passwordEncoder.encode(userRequestDto.getNewPassword());
        user.updatePassword(encodedPassword);

        return new UserResponseDto(user);
    }

    private boolean isValidPasswordFormat(String password) {
        return password.length() >= 8 &&

                password.matches(".*[A-Z].*") &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[!@#$%^&*()].*");
    }



}
