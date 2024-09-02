package com.sparta.starstagram.repository;

import com.sparta.starstagram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 이메일 로그인 중복 가입 방지와 존재여부 파악
     *
     * @author tiyu
     */
    User findByEmail(String email);

    Optional<User> findByNickname(String nickname);


}
