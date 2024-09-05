package com.sparta.starstagram.service;

import com.sparta.starstagram.constans.BaseResponseEnum;
import com.sparta.starstagram.entity.DeletedUser;
import com.sparta.starstagram.entity.User;
import com.sparta.starstagram.exception.HandlePasswordValidateException;
import com.sparta.starstagram.exception.UserJoinIdException;
import com.sparta.starstagram.model.user.UserNewPasswordRequestDto;
import com.sparta.starstagram.model.user.UserRequestDto;
import com.sparta.starstagram.model.user.UserResponseDto;
import com.sparta.starstagram.repository.DeletedUserRepository;
import com.sparta.starstagram.repository.UserRepository;
import com.sparta.starstagram.util.JwtUtil;
import com.sparta.starstagram.util.UtilFind;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.View;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final DeletedUserRepository deletedUserRepository;
    private final UtilFind utilFind;
    private final View error;

    private JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     *
     * @param requestDto
     * @param bindingResult
     * @author tiyu
     */
    @Transactional
    public void registerUser(UserRequestDto requestDto, BindingResult bindingResult) {
        //비밀번호 유효성 검사
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            throw new UserJoinIdException(BaseResponseEnum.USER_PASSWORD_FORMAT);
        }

        //중복된 이메일 체크
        boolean userByEmail = utilFind.userDuplicatedEmail(requestDto.getEmail());
        if (userByEmail) {
            throw new UserJoinIdException(BaseResponseEnum.USER_DUPLICATED);
        }

        //중복된 유저네임 체크
        boolean userByUsername = utilFind.userDuplicatedUsername(requestDto.getUsername());
        if (userByUsername) {
            throw new UserJoinIdException(BaseResponseEnum.USER_USERNAME_DUPLICATED);
        }

        //탈퇴한 이메일 체크
        if (deletedUserRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new UserJoinIdException(BaseResponseEnum.USER_DELETE_EMAIL);
        }

        // 새 사용자 생성 및 저장
        User newUser = new User();
        newUser.updateEmail(requestDto.getEmail());
        newUser.updatePassword(requestDto.getPassword());
        newUser.updateUserName(requestDto.getUsername());


        userRepository.save(newUser);
    }

    /**
     * 회원탈퇴
     *
     * @param user     로그인 한 유저의 정보
     * @param password 회원탈퇴 시 비밀번호 확인
     * @author tiyu
     */
    @Transactional
    public void deleteUser(User user, String password) {
        //비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserJoinIdException(BaseResponseEnum.USER_INVALID_PASSWORD);
        }

        //사용자 삭제
        userRepository.delete(user);

        //DeletedUser 엔티티에 추가
        DeletedUser deletedUser = new DeletedUser(user.getEmail(), LocalDateTime.now());
        deletedUserRepository.save(deletedUser);
    }


    /**
     * 프로필 조회로직  민감한 정보는 제외한 username과 email만 가져옴
     *
     * @param id 조회할 정보
     * @return 조회된 프로필 반환
     * @author 이태건
     */
    @Transactional(readOnly = true)
    public UserResponseDto getUser(Long id) {
        User user = utilFind.userFindById(id);
        return new UserResponseDto(user);
    }


    /**
     * 프로필 수정 로직
     *
     * @param userRequestDto 현재 비밀번호와 새 비밀번호 정보를 담고 있는 DTO
     * @param loginUser      로그인된 사용자 정보를 담고 있는 User 객체
     * @author 이태건
     */
    @Transactional
    public BaseResponseEnum updateUser(UserNewPasswordRequestDto userRequestDto, User loginUser) {
        //입력된 현재 비밀번호 올바른지 확인
        if (!passwordEncoder.matches(userRequestDto.getCurrentPassword(), loginUser.getPassword())) {
            throw new HandlePasswordValidateException(BaseResponseEnum.PASSWORD_MISMATCH);
        }
        //새 비밀번호와 현재 비밀번호가 동일하지 않은지 확인
        if (passwordEncoder.matches(userRequestDto.getNewPassword(), loginUser.getPassword())) {
            throw new HandlePasswordValidateException(BaseResponseEnum.DUPLICATE_NEW_PASSWORD);
        }
        //새 비밀번호 형식이 올바른지 확인
        if (!isValidPasswordFormat(userRequestDto.getNewPassword())) {
            throw new HandlePasswordValidateException(BaseResponseEnum.PASSWORD_FORMAT_NOT_VALID);
        }
        //새로운 비밀번호 인코딩 하여 저장
        String encodedPassword = passwordEncoder.encode(userRequestDto.getNewPassword());
        loginUser.updatePassword(encodedPassword);
        return BaseResponseEnum.USER_PASSWORD_CHANGE_SUCCESS;
    }
    // 정규식에 부합하는 조건이되는지 검사
    private boolean isValidPasswordFormat(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[!@#$%^&*()].*");
    }
}
