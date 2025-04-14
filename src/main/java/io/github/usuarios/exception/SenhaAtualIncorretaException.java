package io.github.usuarios.exception;

public class SenhaAtualIncorretaException extends RuntimeException {
    public SenhaAtualIncorretaException() {
        super("Senha atual incorreta.");
    }
}
