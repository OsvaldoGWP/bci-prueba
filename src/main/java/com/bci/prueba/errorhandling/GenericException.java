package com.bci.prueba.errorhandling;

import lombok.Getter;

@Getter
public class GenericException extends RuntimeException {

    private final int status;

    public GenericException(ErrorCode errorCode, int status) {
        super(errorCode.getMessage());
        this.status = status;
    }

}
