package com.sparta.starstagram.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class DeletedUser extends TimeStamp {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private LocalDateTime deletedAt;

    public DeletedUser(String email, LocalDateTime deletedAt) {
        this.email = email;
        this.deletedAt = deletedAt;
    }
}
