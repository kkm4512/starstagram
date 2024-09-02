package com.sparta.starstagram.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User extends TimeStamp {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String nickname;

    public User(String email, String nickname, String encodedPassword) {
        this.email = email;
        this.password = encodedPassword;
        this.nickname = nickname;
    }

    public void setPassword(String encode) {
        this.password = encode;
    }

}
