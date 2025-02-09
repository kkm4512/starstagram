package com.sparta.starstagram.entity;

import com.sparta.starstagram.constans.UserRoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

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


    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private final List<Post> PostList = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private final List<Follow> followList = new ArrayList<>();


    public User(String email, String username, String encodedPassword) {
        this.email = email;
        this.password = encodedPassword;
        this.username = username;
    }

    // 암호화된 패스워드를 저장
    public void updatePassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateUserName(String username) {
        this.username = username;
    }
}
