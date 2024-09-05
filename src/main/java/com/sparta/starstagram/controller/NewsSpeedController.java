package com.sparta.starstagram.controller;

import com.sparta.starstagram.entity.User;
import com.sparta.starstagram.model.post.ResponsePostDto;
import com.sparta.starstagram.security.UserDetailsImpl;
import com.sparta.starstagram.service.NewsSpeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/newsSpeeds")
@RequiredArgsConstructor
public class NewsSpeedController {
    private final NewsSpeedService newsSpeedService;


    /**
     * 특정 페이지의, 친구들의 게시글을 확인할 수 있는 뉴스피드 컨트롤러
     *
     * @param page 몇번째 페이지
     * @param size 몇개의 게시글
     * @return N번째 페이지, N번째 게시글들을 반환한다
     * @author 김경민
     */
    @GetMapping("/query")
    public Page<ResponsePostDto> getNewsSpeedPostPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User loginUser = userDetails.getUser();
        return newsSpeedService.getNewsSpeedPostPage(page-1,size,loginUser);
    }
}
