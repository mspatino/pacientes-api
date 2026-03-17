package com.consultorio.pacientes.exception;

public class ResourceNotFoundException extends ApiException{

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
