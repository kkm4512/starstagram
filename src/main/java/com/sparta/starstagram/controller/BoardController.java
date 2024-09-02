package com.sparta.starstagram.controller;

import com.sparta.starstagram.model.BaseResponseDto;
import com.sparta.starstagram.model.board.RequestBoardDto;
import com.sparta.starstagram.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    @PostMapping("/{userId}")
    public ResponseEntity<BaseResponseDto> createBoard(@RequestBody RequestBoardDto reqDto) {
        boardService.createBoard(reqDto);
    }
}
