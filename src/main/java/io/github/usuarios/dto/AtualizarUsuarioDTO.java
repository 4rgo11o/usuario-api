package io.github.usuarios.dto;

import io.github.usuarios.validations.UniqueEmail;
import io.github.usuarios.validations.UniqueLogin;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;



public record AtualizarUsuarioDTO(

        @NotBlank(message = "Nome é obrigatório.")
        String nome,

        @NotBlank(message = "E-mail é obrigatório.")
        @Email(message = "E-mail deve ser válido.")
        @UniqueEmail
        String email,

        @NotBlank(message = "Login é obrigatório.")
        @Size(min = 4, max = 20, message = "Login deve ter entre 4 e 20 caracteres.")
        @UniqueLogin
        String login,

        @NotNull(message = "Endereço é obrigatório.")
        @Valid
        EnderecoDTO endereco

) {}
