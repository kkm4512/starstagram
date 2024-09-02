package com.sparta.starstagram.entity;

import com.sparta.starstagram.model.board.RequestBoardDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Board extends TimeStamp {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String detail;

    // Dto -> Entity
    public Board(RequestBoardDto reqDto) {
        this.title = reqDto.getTitle();
        this.detail = reqDto.getDetail();
    }
}
