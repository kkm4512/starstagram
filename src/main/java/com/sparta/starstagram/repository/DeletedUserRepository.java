package com.sparta.starstagram.repository;

import com.sparta.starstagram.entity.DeletedUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeletedUserRepository extends JpaRepository<DeletedUser, Long> {
    Optional<DeletedUser> findByEmail(String email);
}
