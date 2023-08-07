package com.example.mutsaSNS.exception;

import com.example.mutsaSNS.domain.entity.enums.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    ALREADY_USER_USERNAME(ResultCode.CONFLICT, "이미 존재하는 사용자입니다."),

    NOT_FOUND_USER(ResultCode.NOT_FOUND, "존재하지 않는 유저입니다."),
    NOT_FOUND_POST(ResultCode.NOT_FOUND, "존재하지 않는 게시글입니다."),
    NOT_FOUND_IMAGE(ResultCode.NOT_FOUND, "존재하지 않는 사진입니다."),
    NOT_FOUND_COMMENT(ResultCode.NOT_FOUND, "존재하지 않는 댓글입니다."),

    NOT_MATCH_POST_USER(ResultCode.FORBIDDEN, "게시글을 등록한 유저가 아닙니다."),
    NOT_MATCH_COMMENT_USER(ResultCode.FORBIDDEN, "댓글을 등록한 유저가 아닙니다."),

    SERVER_ERROR(ResultCode.INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다.");

    private final ResultCode resultCode;
    private final String message;
}
