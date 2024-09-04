package com.sparta.starstagram.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userFriendId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    private User friend;


//    public void addRelation(User loginUser, User friend) {
//        this.user = loginUser;
//        this.friend = friend;
//    }

    public Friend(User loginUser, User friend) {
        this.user = loginUser;
        this.friend = friend;
    }

}
