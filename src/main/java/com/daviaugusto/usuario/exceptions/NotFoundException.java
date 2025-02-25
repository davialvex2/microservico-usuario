package com.daviaugusto.usuario.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String msg){
        super(msg);
    }

    public NotFoundException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
