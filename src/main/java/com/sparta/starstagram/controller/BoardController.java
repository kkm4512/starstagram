package com.sparta.starstagram.controller;

import com.sparta.starstagram.model.BaseResponseDto;
import com.sparta.starstagram.model.board.RequestBoardDto;
import com.sparta.starstagram.model.board.ResponseBoardDto;
import com.sparta.starstagram.security.UserDetailsImpl;
import com.sparta.starstagram.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/test")
    public void test(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println(userDetails.getEmail());
        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.getAuthorities());
    }

    /**
     * // 게시글 생성 API
     * TODO 어떠한 유저가 게시글을 작성하려는 것인지 알아야함
     *
     * @author 김경민
     */
    @PostMapping
    public ResponseEntity<BaseResponseDto> createBoard(@RequestBody RequestBoardDto reqDto, HttpServletRequest req) {
        return boardService.createBoard(reqDto,req);
    }

    /**
     * 특정 게시글 수정 API
     * TODO 게시글의 작성자가 수정하려는 것인지 확인해야함
     *
     * @author 김경민
     */
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseDto> updateBoard(@PathVariable Long id, @RequestBody RequestBoardDto reqDto, HttpServletRequest req) {
        return boardService.updateBoard(id,reqDto,req);
    }

    /**
     * 특정 게시글 수정 API
     * TODO 게시글의 작성자가 삭제하려는 것인지 확인해야함
     *
     * @author 김경민
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDto> deleteBoard(@PathVariable Long id, HttpServletRequest req) {
        return boardService.deleteBoard(id,req);
    }

    /**
     * 게시글 조회 API
     *
     * @author 김경민
     */
    @GetMapping("/{id}")
    public ResponseBoardDto getBoard(@PathVariable Long id) {
        return boardService.getBoard(id);
    }
}
