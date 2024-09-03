package com.sparta.starstagram.model.post;

import com.sparta.starstagram.entity.Post;
import com.sparta.starstagram.model.TimeStampDto;
import lombok.Getter;

@Getter
public class ResponseBoardDto extends TimeStampDto {
    private long id;
    private String title;
    private String detail;

    // Entity -> Dto
    public ResponseBoardDto(Post board) {
        super(board.getCreatedAt(), board.getUpdatedAt());
        this.id = board.getId();
        this.title = board.getTitle();
        this.detail = board.getDetail();
    }
}
