package io.github.usuarios.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum RoleName {
    PROPRIETARIO,
    CLIENTE;

    @JsonCreator
    public static RoleName fromString(String value) {

        return RoleName.valueOf(value.toUpperCase());

    }
}
