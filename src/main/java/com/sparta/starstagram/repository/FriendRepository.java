package com.sparta.starstagram.repository;


import com.sparta.starstagram.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}
