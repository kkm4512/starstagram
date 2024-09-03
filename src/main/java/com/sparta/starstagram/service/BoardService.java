package com.sparta.starstagram.service;

import com.sparta.starstagram.constans.BaseResponseEnum;
import com.sparta.starstagram.entity.Board;
import com.sparta.starstagram.entity.User;
import com.sparta.starstagram.exception.HandleNotFoundException;
import com.sparta.starstagram.model.BaseResponseDto;
import com.sparta.starstagram.model.board.RequestBoardDto;
import com.sparta.starstagram.model.board.ResponseBoardDto;
import com.sparta.starstagram.repository.BoardRepository;
import com.sparta.starstagram.util.JwtUtil;
import com.sparta.starstagram.util.UtilFind;
import com.sparta.starstagram.util.UtilResponse;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UtilFind utilFind;
    private final JwtUtil jwtUtil;

    /**
     * 특정 사용자의 게시글 저장 로직
     *
     * @param reqDto 저장할 게시글 내용
     * @return 성공하였다는 응답 반환
     */
    @Transactional
    public ResponseEntity<BaseResponseDto> createBoard(RequestBoardDto reqDto, HttpServletRequest req) {
        String jwt = jwtUtil.resolveToken(req);
        Claims info = jwtUtil.getUserInfoFromToken(jwt);
        String username = info.getId();
        User user = utilFind.userFindByUsername(username);
        Board board = new Board(reqDto);
//         연관관계 설정
        board.addUser(user);
        boardRepository.save(board);
        return UtilResponse.getResponseEntity(BaseResponseEnum.BOARD_UPDATE_SUCCESS);
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
    public ResponseEntity<BaseResponseDto> updateBoard(Long id, RequestBoardDto reqDto, HttpServletRequest req) {
        String jwt = jwtUtil.resolveToken(req);
        Claims info = jwtUtil.getUserInfoFromToken(jwt);
        String username = info.getId();
        User user = utilFind.userFindByUsername(username);
        Board board = utilFind.boardFindById(id);
        System.out.println(user.getUsername());
        System.out.println(board.getUser().getUsername());
        if (user.getId().equals(board.getUser().getId())) {}
        board.updateBoard(reqDto);
        return UtilResponse.getResponseEntity(BaseResponseEnum.BOARD_UPDATE_SUCCESS);
    }

    /**
     * 특정 게시글 수정 로직
     *
     * @param id 수정할 게시글 ID
     * @return 성공하였다는 응답 반환
     * @throws HandleNotFoundException 게시글이 없을시 발생되는 예외
     */
    @Transactional
    public ResponseEntity<BaseResponseDto> deleteBoard(Long id, HttpServletRequest req) {
        Board board = utilFind.boardFindById(id);
        boardRepository.delete(board);
        return UtilResponse.getResponseEntity(BaseResponseEnum.BOARD_DELETE_SUCCESS);
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
        Board board = utilFind.boardFindById(id);
        return new ResponseBoardDto(board);
    }
}
