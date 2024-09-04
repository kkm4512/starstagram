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
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    /**
     * 게시글 생성 API
     *
     * @author 김경민
     */
     @PostMapping
    public ResponseEntity<BaseResponseDto> createPost(@RequestBody RequestPostDto reqDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User loginUser = userDetails.getUser();
        BaseResponseEnum responseEnum = postService.createPost(reqDto,loginUser);
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
        BaseResponseEnum responseEnum = postService.updatePost(id,reqDto,loginUser);
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
        BaseResponseEnum responseEnum = postService.deletePost(id,loginUser);
        return UtilResponse.getResponseEntity(responseEnum);
    }

    /**
     * 게시글 조회 API
     *
     * @author 김경민
     */
    @GetMapping("/{id}")
    public ResponsePostDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    /**
     * 자신의 게시글 + 친구의 게시글 열람 가능 API
     *
     * @param page 몇번째 페이지
     * @param size 몇개의 게시글
     * @param userDetails 로그인 되있는 사용자
     * @return 조건에 부합하는 게시글들 반환
     */
    @GetMapping("/query")
    public Page<ResponsePostDto> getPostPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User user = userDetails.getUser();
        return postService.getPostPage(page-1,size,user);
    }


}
