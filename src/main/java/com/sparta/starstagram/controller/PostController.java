package com.sparta.starstagram.controller;

import com.sparta.starstagram.constans.BaseResponseEnum;
import com.sparta.starstagram.entity.User;
import com.sparta.starstagram.model.BaseResponseDto;
import com.sparta.starstagram.model.post.RequestPostDto;
import com.sparta.starstagram.model.post.ResponsePostDto;
import com.sparta.starstagram.security.UserDetailsImpl;
import com.sparta.starstagram.service.PostService;
import com.sparta.starstagram.util.UtilResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService boardService;
    /**
     * 게시글 생성 API
     *
     * @author 김경민
     */
    @PostMapping
    public ResponseEntity<BaseResponseDto> createPost(@RequestBody RequestPostDto reqDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User loginUser = userDetails.getUser();
        BaseResponseEnum responseEnum = boardService.createPost(reqDto,loginUser);
        return UtilResponse.getResponseEntity(responseEnum);
    }

    /**
     * 특정 게시글 수정 API
     *
     * @author 김경민
     */
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseDto> updatePost(@PathVariable Long id, @RequestBody RequestPostDto reqDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User loginUser = userDetails.getUser();
        BaseResponseEnum responseEnum = boardService.updatePost(id,reqDto,loginUser);
        return UtilResponse.getResponseEntity(responseEnum);
    }

    /**
     * 특정 게시글 수정 API
     *
     * @author 김경민
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDto> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User loginUser = userDetails.getUser();
        BaseResponseEnum responseEnum = boardService.deletePost(id,loginUser);
        return UtilResponse.getResponseEntity(responseEnum);
    }

    /**
     * 게시글 조회 API
     *
     * @author 김경민
     */
    @GetMapping("/{id}")
    public ResponsePostDto getPost(@PathVariable Long id) {
        return boardService.getPost(id);
    }
}
