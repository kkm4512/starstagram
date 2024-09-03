package com.sparta.starstagram.service;

import com.sparta.starstagram.constans.BaseResponseEnum;
import com.sparta.starstagram.entity.Post;
import com.sparta.starstagram.entity.User;
import com.sparta.starstagram.exception.HandleNotFoundException;
import com.sparta.starstagram.model.post.RequestBoardDto;
import com.sparta.starstagram.model.post.ResponseBoardDto;
import com.sparta.starstagram.repository.PostRepository;
import com.sparta.starstagram.util.JwtUtil;
import com.sparta.starstagram.util.UtilFind;
import com.sparta.starstagram.util.UtilValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository boardRepository;
    private final UtilFind utilFind;
    private final JwtUtil jwtUtil;

    /**
     * 특정 사용자의 게시글 저장 로직
     *
     * @param reqDto 저장할 게시글 내용
     * @return 성공하였다는 응답 반환
     */
    @Transactional
    public BaseResponseEnum createBoard(RequestBoardDto reqDto, User loginUser) {
        Post board = new Post(reqDto);
        // 연관관계 설정
        board.addUser(loginUser);
        boardRepository.save(board);
        return BaseResponseEnum.BOARD_SAVE_SUCCESS;
    }

    /**
     * 특정 게시글 수정 로직
     *
     * @param id 수정할 게시글 ID
     * @param reqDto 수정할 게시글 내용
     * @return 성공하였다는 응답 반환
     * @throws HandleNotFoundException 게시글이 없을시 발생되는 예외
     */
    @Transactional
    public BaseResponseEnum updateBoard(Long id, RequestBoardDto reqDto, User loginUser) {
        Post board = utilFind.boardFindById(id);
        UtilValidator.isSameUser(loginUser,board.getUser());
        board.updateBoard(reqDto);
        return BaseResponseEnum.BOARD_UPDATE_SUCCESS;
    }

    /**
     * 특정 게시글 수정 로직
     *
     * @param id 수정할 게시글 ID
     * @return 성공하였다는 응답 반환
     * @throws HandleNotFoundException 게시글이 없을시 발생되는 예외
     */
    @Transactional
    public BaseResponseEnum deleteBoard(Long id, User loginUser) {
        Post board = utilFind.boardFindById(id);
        UtilValidator.isSameUser(loginUser,board.getUser());
        boardRepository.delete(board);
        return BaseResponseEnum.BOARD_DELETE_SUCCESS;
    }

    /**
     * 특정 게시글 조회 로직
     *
     * @param id 조회할 게시글 ID
     * @return 조회된 게시글 반환
     * @throws HandleNotFoundException 게시글이 없을시 발생되는 예외
     */
    @Transactional(readOnly = true)
    public ResponseBoardDto getBoard(Long id) {
        Post board = utilFind.boardFindById(id);
        return new ResponseBoardDto(board);
    }
}
