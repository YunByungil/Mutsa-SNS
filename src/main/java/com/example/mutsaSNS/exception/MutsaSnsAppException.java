package com.example.mutsaSNS.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MutsaSnsAppException extends RuntimeException{

    private ErrorCode errorCode;

    public MutsaSnsAppException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
