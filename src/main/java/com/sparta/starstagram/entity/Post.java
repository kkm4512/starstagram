package com.sparta.starstagram.entity;

import com.sparta.starstagram.model.post.RequestPostDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Post extends TimeStamp {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String detail;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 연관관계 설정
    public void addUser(User user){
        this.user = user;
    }

    // 게시글 수정
    public void updateBoard(RequestPostDto reqDto){
        this.title = reqDto.getTitle();
        this.detail = reqDto.getDetail();
    }

    // Dto -> Entity
    public Post(RequestPostDto reqDto) {
        this.title = reqDto.getTitle();
        this.detail = reqDto.getDetail();
    }
}
