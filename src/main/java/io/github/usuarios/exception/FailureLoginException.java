package io.github.usuarios.exception;


public class FailureLoginException extends RuntimeException{
    public FailureLoginException(String msg){
        super(msg);
    }
}
