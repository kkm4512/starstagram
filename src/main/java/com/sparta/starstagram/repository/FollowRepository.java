package com.sparta.starstagram.repository;


import com.sparta.starstagram.entity.Follow;
import com.sparta.starstagram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Follow findByUserAndFollow(User user, User follow);
}
