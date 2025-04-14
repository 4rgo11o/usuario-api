package io.github.usuarios.exception;

public class TokenAusenteException extends RuntimeException {
    public TokenAusenteException(String msg){
        super(msg);
    }
}
