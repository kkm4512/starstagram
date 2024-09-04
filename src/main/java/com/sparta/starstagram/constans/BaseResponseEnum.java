package com.sparta.starstagram.constans;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BaseResponseEnum {
    // 공용
    SUCCESS(true, HttpStatus.OK.value(), "요청에 성공 하였습니다"),
    FAIL(false, HttpStatus.BAD_REQUEST.value(), "요청에 실패 하였습니다"),

    // User
    USER_SAVE_SUCCESS(true, HttpStatus.OK.value(), "회원가입에 성공 하였습니다"),
    USER_DELETE_SUCCESS(true, HttpStatus.OK.value(), "회원 탈퇴에 성공 하였습니다"),
    USER_LOGIN_SUCCESS(true, HttpStatus.OK.value(), "로그인에 성공 하였습니다"),
    USER_PASSWORD_CHANGE_SUCCESS(true,HttpStatus.OK.value(), "비밀번호 변경에 성공 하였습니다"),
    USER_NOT_FOUND(false, HttpStatus.NOT_FOUND.value(), "요청 하는 유저가 존재하지 않습니다"),
    USER_INVALID_CREDENTIALS(false, HttpStatus.UNAUTHORIZED.value(), "아이디 또는 비밀번호가 틀렸습니다"),
    USER_MISMATCH_BOARD(false, HttpStatus.BAD_REQUEST.value(), "사용자가 요청한 게시글은, 본인이 작성한 게시글이 아닙니다"),
    USER_DUPLICATED(false, HttpStatus.CONFLICT.value(), "중복된 이메일 입니다"),
    USER_USERNAME_DUPLICATED(false, HttpStatus.CONFLICT.value(), "중복된 닉네임 입니다"),
    USER_PASSWORD_FORMAT(false, HttpStatus.BAD_REQUEST.value(), "틀림 ㅅㄱ"),
    USER_PASSWORD_CHANGE_FAIL(false,HttpStatus.BAD_REQUEST.value(),"비밀번호 변경에 실패 하였습니다"),

    //FOLLOW
    FOLLOW_SUCCESS(true,HttpStatus.OK.value(), "팔로우에 성공 하였습니다"),
    FOLLOW_FAIL(true,HttpStatus.BAD_REQUEST.value(), "팔로우에 실패 하였습니다"),
    FOLLOW_DELETE_SUCCESS(true,HttpStatus.OK.value(), "팔로우를 취소 하였습니다"),
    FOLlOW_DELETE_FAIL(true,HttpStatus.BAD_REQUEST.value(), "팔로우 취소에 실패 하였습니다"),


    // POST
    POST_SAVE_SUCCESS(true, HttpStatus.OK.value(), "게시글 저장에 성공 하였습니다"),
    POST_UPDATE_SUCCESS(true, HttpStatus.OK.value(), "게시글 수정에 성공 하였습니다"),
    POST_DELETE_SUCCESS(true, HttpStatus.OK.value(), "게시글 삭제에 성공 하였습니다"),
    POST_NOT_FOUND(false, HttpStatus.NOT_FOUND.value(), "요청 하는 게시글이 존재하지 않습니다"),

    // Jwt
    JWT_NOT_FOUND(false, HttpStatus.NOT_FOUND.value(), "Jwt가 존재하지 않습니다"),
    JWT_EXPIRED(false, HttpStatus.UNAUTHORIZED.value(), "Jwt가 만료되었습니다"),
    JWT_MALFORMED(false, HttpStatus.UNAUTHORIZED.value(), "Jwt가 손상 되었습니다"),
    JWT_UNSUPPORTED(false, HttpStatus.UNAUTHORIZED.value(), "지원되지 않는 Jwt 입니다"),
    JWT_SIGNATURE_FAIL(false, HttpStatus.UNAUTHORIZED.value(), "시그니처 검증에 실패한 Jwt 입니다"),
    JWT_NOT_VALID(false, HttpStatus.UNAUTHORIZED.value(), "Jwt가 유효하지 않습니다"),

    // Auth
    NOT_ADMIN(false, HttpStatus.FORBIDDEN.value(), "관리자만 접속 가능 합니다"),

    // Encode
    UNSUPPORTED_ENCODING(false, HttpStatus.BAD_REQUEST.value(), "클라이언트로 부터 들어온 형식은, 인코딩 할 수 없는 형식 입니다");


    private final boolean success;
    private final int status;
    private final String message;

    BaseResponseEnum(boolean success, int status, String message) {
        this.success = success;
        this.status = status;
        this.message = message;
    }
}
