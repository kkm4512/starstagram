package com.sparta.starstagram.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DeletedUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;

    @CreatedDate
    private LocalDateTime deletedAt;

    public DeletedUser(String email, LocalDateTime deletedAt) {
        this.email = email;
        this.deletedAt = deletedAt;
    }
}
