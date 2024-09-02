package com.sparta.starstagram.service;

import com.sparta.starstagram.dto.UserRequestDto;
import com.sparta.starstagram.dto.UserResponseDto;
import com.sparta.starstagram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.sparta.starstagram.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto getUser(Long id) {
        User user = (User) userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return new UserResponseDto(user);
    }


    }
}
