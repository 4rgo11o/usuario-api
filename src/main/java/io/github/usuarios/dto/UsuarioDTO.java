package io.github.usuarios.dto;

import io.github.usuarios.entities.enums.RoleName;
import io.github.usuarios.validations.UniqueCpf;
import io.github.usuarios.validations.UniqueEmail;
import io.github.usuarios.validations.UniqueLogin;
import io.github.usuarios.validations.ValidRoleNames;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import java.util.List;



public record UsuarioDTO(

        @NotBlank(message = "Nome é obrigatório.")
        String nome,

        @NotBlank(message = "E-mail é obrigatório.")
        @Email(message = "E-mail deve ser válido.")
        @UniqueEmail
        String email,

        @NotBlank(message = "CPF é obrigatório.")
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos.")
        @UniqueCpf
        String cpf,

        @NotBlank(message = "Login é obrigatório.")
        @Size(min = 4, max = 20, message = "Login deve ter entre 4 e 20 caracteres.")
        @UniqueLogin
        String login,

        @NotBlank(message = "Senha é obrigatória.")
        String senha,

        @NotNull(message = "Endereço é obrigatório.")
        @Valid
        EnderecoDTO endereco,

        @ValidRoleNames
        List<RoleName> roles

) {}
