package com.example.mutsaSNS.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ErrorResponse {

    private String errorCode;
    private String message;
}
