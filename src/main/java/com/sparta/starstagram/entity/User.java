package com.sparta.starstagram.entity;

import com.sparta.starstagram.constans.UserRoleEnum;
import com.sparta.starstagram.model.user.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Getter
@NoArgsConstructor
public class User extends TimeStamp {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoleEnum role = UserRoleEnum.USER;


    public User(UserRequestDto requestDto) {
        this.email = requestDto.getEmail();
        this.username = requestDto.getUsername();
        this.password = new BCryptPasswordEncoder().encode(requestDto.getPassword());
    }


    public User(String email, String username, String encodedPassword) {
        this.email = email;
        this.password = encodedPassword;
        this.username = username;
    }

    // 암호화된 패스워드를 저장
    public void updatePassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

}
