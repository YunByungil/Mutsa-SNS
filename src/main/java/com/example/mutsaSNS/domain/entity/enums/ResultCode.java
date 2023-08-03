package com.example.mutsaSNS.domain.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ResultCode {

    SUCCESS(HttpStatus.OK),
    CONFLICT(HttpStatus.CONFLICT);

    private final HttpStatus httpStatus;
}
