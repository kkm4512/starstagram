package com.sparta.starstagram.repository;


import com.sparta.starstagram.entity.Friend;
import com.sparta.starstagram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByUser(User user);
}
