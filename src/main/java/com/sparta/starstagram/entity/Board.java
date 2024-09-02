package com.sparta.starstagram.entity;

import com.sparta.starstagram.model.board.RequestBoardDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Board extends TimeStamp {
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
    public void updateBoard(RequestBoardDto reqDto){
        this.title = reqDto.getTitle();
        this.detail = reqDto.getDetail();
    }

    // Dto -> Entity
    public Board(RequestBoardDto reqDto) {
        this.title = reqDto.getTitle();
        this.detail = reqDto.getDetail();
    }
}
