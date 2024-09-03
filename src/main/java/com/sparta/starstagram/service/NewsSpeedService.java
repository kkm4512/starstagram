package com.sparta.starstagram.service;

import com.sparta.starstagram.entity.Post;
import com.sparta.starstagram.model.post.ResponseBoardDto;
import com.sparta.starstagram.repository.PageNavigateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsSpeedService {
    private final PageNavigateRepository pageNavigateRepository;

    /**
     * 사용자가 요청한 페이지의 게시글들 가져옴
     *
     * @param page 가져올 페이지 (디폴트는 1페이지)
     * @param size 가져올 게시글 갯수 (디폴트는 10페이지)
     * @return Page<ResponseBoardDto> N page, N size의 board들을 반환
     *
     * TODO Friend에 있는 id의 게시글들만 가져와야함
     */
    public Page<ResponseBoardDto> getBoardPage(int page, int size) {
        Sort.Direction dir = Sort.Direction.DESC;
        Sort sort = Sort.by(dir,"createdAt");
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Post> boardList = pageNavigateRepository.findAll(pageable);
        return boardList.map(ResponseBoardDto::new);
    }
}
