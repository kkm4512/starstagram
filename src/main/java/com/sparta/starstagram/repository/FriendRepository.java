package com.sparta.starstagram.repository;


import com.sparta.starstagram.entity.Friend;
import com.sparta.starstagram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    Friend findByUserAndFriend(User user, User friend);
}
