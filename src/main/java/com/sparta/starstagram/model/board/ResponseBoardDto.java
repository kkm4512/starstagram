package com.sparta.starstagram.model.board;

import com.sparta.starstagram.entity.Board;
import com.sparta.starstagram.model.TimeStampDto;
import lombok.Getter;

@Getter
public class ResponseBoardDto extends TimeStampDto {
    private long id;
    private String title;
    private String detail;

    // Entity -> Dto
    public ResponseBoardDto(Board board) {
        super(board.getCreatedAt(), board.getUpdatedAt());
        this.id = board.getId();
        this.title = board.getTitle();
        this.detail = board.getDetail();
    }
}
