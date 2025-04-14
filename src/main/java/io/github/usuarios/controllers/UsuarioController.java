package io.github.usuarios.controllers;


import io.github.usuarios.dto.*;
import io.github.usuarios.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name="Usuário", description = "Controller para realizar operações de cadastro, login, alteração de senha, atualização e exclusão de usuário.")
public class UsuarioController {



    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @Operation(summary="Cadastra usuário", description = "Registra um novo usuário no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados duplicados (login, CPF, e-mail).",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ValidationErrorDTO.class)))
    })

    public ResponseEntity<ResponseStatusDTO> criarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        usuarioService.createUser(usuarioDTO);
        ResponseStatusDTO resposta = new ResponseStatusDTO("Usuário cadastrado com sucesso.", 201);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PostMapping("/login")
    @Operation(summary="Autentica usuário", description = "Gera um token JWT com as credenciais do usuário.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso.",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = RecoveryJwtTokenDto.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas.",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseStatusDTO.class)))
    })
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginDTO loginUserDto) {
        var token = usuarioService.authenticateUser(loginUserDto);
        RecoveryJwtTokenDto resposta = new RecoveryJwtTokenDto(token, "Usuário autenticado com sucesso.", 200);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }


    @PutMapping("/alterar-senha")
    @Operation(summary="Altera senha", description = "Altera senha do usuário." +
        "Requer envio de token JWT válido no header Authorization.",
            security = { @SecurityRequirement(name = "bearerAuth") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Senha atual incorreta.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseStatusDTO.class))),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseStatusDTO.class))),
    })
    public ResponseEntity<ResponseStatusDTO> alterarSenha(
            @RequestBody AlterarSenhaDTO dto
    ) {
        usuarioService.alterarSenha(dto);
        ResponseStatusDTO resposta = new ResponseStatusDTO("Senha alterada com sucesso.", 200);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PutMapping("/atualizar-usuario")
    @Operation(
            summary = "Atualiza informações do usuário autenticado",
            description = "Permite ao usuário autenticado atualizar seus dados cadastrais. " +
                    "Requer envio de token JWT válido no header Authorization.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados incorretos.",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ValidationErrorDTO.class))),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseStatusDTO.class))),
    })
    public ResponseEntity<ResponseStatusDTO> atualizarUsuario(@Valid @RequestBody AtualizarUsuarioDTO dto) {
        usuarioService.atualizarUsuario(dto);
        ResponseStatusDTO resposta = new ResponseStatusDTO("Usuário atualizado com sucesso.", 200);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @DeleteMapping("/deletar-usuario")
    @Operation(summary="Deleta usuário", description = "Deleta usuario do sistema. " +
            "Requer envio de token JWT válido no header Authorization.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso."),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseStatusDTO.class))),
    })
    public ResponseEntity<?> deletarUsuario() {

            usuarioService.deletarUsuario();
            return ResponseEntity.status(204).build();

    }
}