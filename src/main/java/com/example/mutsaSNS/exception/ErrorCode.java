package com.example.mutsaSNS.exception;

import com.example.mutsaSNS.domain.entity.enums.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    ALREADY_USER_USERNAME(ResultCode.CONFLICT, "이미 존재하는 사용자입니다.");

    private final ResultCode resultCode;
    private final String message;
}
