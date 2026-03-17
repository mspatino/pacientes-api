package com.consultorio.pacientes.exception;

public class BusinessException extends ApiException {

    public BusinessException(String message) {
        super(message);
    }
}