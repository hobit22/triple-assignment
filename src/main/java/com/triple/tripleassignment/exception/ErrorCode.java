package com.triple.tripleassignment.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    OK(HttpStatus.OK, "200", "true"),

    //Not Found
    NOT_FOUND_USER_ID(HttpStatus.BAD_REQUEST, "400", "존재하지 않는 유저 아이디입니다."),
    NOT_FOUND_PLACE_ID(HttpStatus.BAD_REQUEST, "400", "존재하지 않는 장소 아이디입니다."),
    NOT_FOUND_REVIEW_ID(HttpStatus.BAD_REQUEST, "400", "존재하지 않는 리뷰 아이디입니다."),
    NOT_FOUND_IMAGE_ID(HttpStatus.BAD_REQUEST, "400", "존재하지 않는 이미지 아이디입니다."),
    // 회원가입
    SIGNUP_USER_ID_DUPLICATE(HttpStatus.BAD_REQUEST, "400", "해당 아이디가 이미 존재합니다."),

    // color가 theme에 존재하지 않음
    NOT_AUTHOR(HttpStatus.BAD_REQUEST, "400", "작성자가 아닙니다."),
    ALREADY_EXIST_REVIEW(HttpStatus.BAD_REQUEST, "400", "이 유저는 이 곳에 이미 리뷰를 남겼습니다."),
    NOT_SAME_REVIEW_AND_PLACE(HttpStatus.BAD_REQUEST, "400", "리뷰와 장소가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String errorMessage;
}