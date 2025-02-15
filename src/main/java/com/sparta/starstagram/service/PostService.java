package com.sparta.starstagram.service;

import com.sparta.starstagram.constans.BaseResponseEnum;
import com.sparta.starstagram.entity.Post;
import com.sparta.starstagram.entity.User;
import com.sparta.starstagram.exception.HandleNotFoundException;
import com.sparta.starstagram.exception.UserMismatchException;
import com.sparta.starstagram.model.post.RequestPostDto;
import com.sparta.starstagram.model.post.ResponsePostDto;
import com.sparta.starstagram.repository.PageNavigateRepository;
import com.sparta.starstagram.repository.PostRepository;
import com.sparta.starstagram.util.UtilFind;
import com.sparta.starstagram.util.UtilValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PageNavigateRepository pageNavigateRepository;
    private final UtilFind utilFind;

    /**
     * 특정 사용자의 게시글 저장 로직
     *
     * @param reqDto 저장할 게시글 내용
     * @return 성공하였다는 응답 반환
     */
    @Transactional
    public BaseResponseEnum createPost(RequestPostDto reqDto, User loginUser) {
        Post post = new Post(reqDto);
        post.addUser(loginUser); // 연관관계 설정
        postRepository.save(post);
        return BaseResponseEnum.POST_SAVE_SUCCESS;
    }

    /**
     * 특정 게시글 수정 로직
     *
     * @param id 수정할 게시글 ID
     * @param reqDto 수정할 게시글 내용
     * @return 성공하였다는 응답
     * @throws HandleNotFoundException 게시글이 없을시 발생되는 예외
     * @throws UserMismatchException 유저가 다른 게시글을 수정,삭제 할때 발생되는 예외
     */
    @Transactional
    public BaseResponseEnum updatePost(Long id, RequestPostDto reqDto, User loginUser) {
        Post post = utilFind.postFindById(id);
        UtilValidator.isSameUser(loginUser,post.getUser());
        post.updatePost(reqDto);
        return BaseResponseEnum.POST_UPDATE_SUCCESS;
    }

    /**
     * 특정 게시글 수정 로직
     *
     * @param id 수정할 게시글 ID
     * @return 성공하였다는 응답 반환
     * @throws HandleNotFoundException 게시글이 없을시 발생되는 예외
     * @throws UserMismatchException 유저가 다른 게시글을 수정,삭제 할때 발생되는 예외
     */
    @Transactional
    public BaseResponseEnum deletePost(Long id, User loginUser) {
        Post post = utilFind.postFindById(id);
        UtilValidator.isSameUser(loginUser,post.getUser());
        postRepository.delete(post);
        return BaseResponseEnum.POST_DELETE_SUCCESS;
    }

    /**
     * 특정 게시글 조회 로직
     *
     * @param id 조회할 게시글 ID
     * @return 조회된 게시글 반환
     * @throws HandleNotFoundException 게시글이 없을시 발생되는 예외
     */
    @Transactional(readOnly = true)
    public ResponsePostDto getPost(Long id) {
        Post board = utilFind.postFindById(id);
        return new ResponsePostDto(board);
    }

    /**
     * 로그인한 사용자와 + 친구의 게시글들만 가져옴 작성일기준 내림차순으로 가져옴
     *
     * @param page 가져올 페이지 (디폴트는 1페이지)
     * @param size 가져올 게시글 갯수 (디폴트는 10페이지)
     * @return Page<ResponsePostDto> N page, N size의 post들을 반환
     *
     */
    public Page<ResponsePostDto> getPostPage(int page, int size, User user) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postList = pageNavigateRepository.findPostsByUserAndUserFollows(user,pageable);
        return postList.map(ResponsePostDto::new);
    }
}
