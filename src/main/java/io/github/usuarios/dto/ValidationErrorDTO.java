package io.github.usuarios.dto;

import java.util.List;

public record ValidationErrorDTO(List<String> errors, int status) {

}