package com.swpu.contactserver.common.exceptions;

public class UnAuthException extends RuntimeException{
    public UnAuthException(){
        super();
    }
    public UnAuthException(String message){
        super(message);
    }
}
