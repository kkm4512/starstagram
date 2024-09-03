package com.sparta.starstagram.controller;

import com.sparta.starstagram.model.post.ResponsePostDto;
import com.sparta.starstagram.service.NewsSpeedService;
import com.sparta.starstagram.util.UtilFind;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NewsSpeedController {
    private final NewsSpeedService pageNavigateService;
    private final UtilFind utilFind;


    /**
     * 특정 페이지의, 특정 갯수만큼 게시글 찾을 수 있는 API
     *
     * @param page 몇번째 페이지
     * @param size 몇개의 게시글
     * @return N번째 페이지, N번째 게시글들을 반환한다
     * @author 김경민
     */
    @GetMapping("/query")
    public Page<ResponsePostDto> getBoardPage(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, HttpServletRequest req) {
        return pageNavigateService.getBoardPage(page-1,size);
    }
}
