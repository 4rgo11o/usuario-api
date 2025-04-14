package io.github.usuarios.dto;

import jakarta.validation.constraints.NotBlank;


public record EnderecoDTO(

        @NotBlank(message = "Nome da rua é obrigatório.")
        String rua,

        @NotBlank(message = "Numero da rua é obrigatório.")
        String numero,

        @NotBlank(message = "Cep é obrigatório.")
        String cep,

        @NotBlank(message = "Nome da cidade é obrigatório.")
        String cidade,

        @NotBlank(message = "Nome da estado é obrigatório.")
        String estado

) {}