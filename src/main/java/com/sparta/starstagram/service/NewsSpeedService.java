package com.sparta.starstagram.service;

import com.sparta.starstagram.entity.Post;
import com.sparta.starstagram.entity.User;
import com.sparta.starstagram.model.post.ResponsePostDto;
import com.sparta.starstagram.repository.PageNavigateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsSpeedService {
    private final PageNavigateRepository pageNavigateRepository;

    /**
     * 로그인한 사용자와 친구 관계에 있는 게시글들만 가져옴 (자신도 제외), 작성일기준 내림차순으로 가져옴
     *
     * @param page 가져올 페이지 (디폴트는 1페이지)
     * @param size 가져올 게시글 갯수 (디폴트는 10페이지)
     * @return Page<ResponsePostDto> N page, N size의 board들을 반환
     *
     */
    public Page<ResponsePostDto> getNewsSpeedPostPage(int page, int size, User loginUser) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Post> postList = pageNavigateRepository.findPostsByUserFriends(loginUser,pageable);
        return postList.map(ResponsePostDto::new);
    }
}
