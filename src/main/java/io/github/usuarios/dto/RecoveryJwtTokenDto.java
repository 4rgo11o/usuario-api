package io.github.usuarios.dto;

public record RecoveryJwtTokenDto(

        String token,
        String msg,
        int status

) {
}
