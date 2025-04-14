package io.github.usuarios.services;

import io.github.usuarios.security.JwtTokenService;
import io.github.usuarios.config.SecurityConfiguration;
import io.github.usuarios.dto.*;
import io.github.usuarios.entities.Endereco;
import io.github.usuarios.entities.Role;
import io.github.usuarios.entities.Usuario;
import io.github.usuarios.exception.FailureLoginException;
import io.github.usuarios.exception.SenhaAtualIncorretaException;
import io.github.usuarios.repository.RoleRepository;
import io.github.usuarios.repository.UsuarioRepository;
import io.github.usuarios.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void createUser(UsuarioDTO usuarioDto) {
        Usuario novoUsuario = mapToEntity(usuarioDto);
        usuarioRepository.save(novoUsuario);
    }


    public String authenticateUser(LoginDTO loginDTO) {
        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginDTO.login(), loginDTO.senha());

            Authentication authentication = authenticationManager.authenticate(authToken);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            return jwtTokenService.generateToken(userDetails);

        } catch (BadCredentialsException ex) {
            throw new FailureLoginException("Login ou senha inválidos.");
        }
    }

    private Usuario mapToEntity(UsuarioDTO usuarioDto) {


        Endereco endereco = new Endereco();
        endereco.setRua(usuarioDto.endereco().rua());
        endereco.setNumero(usuarioDto.endereco().numero());
        endereco.setCidade(usuarioDto.endereco().cidade());
        endereco.setEstado(usuarioDto.endereco().estado());
        endereco.setCep(usuarioDto.endereco().cep());

        return Usuario.builder()
                .nome(usuarioDto.nome())
                .email(usuarioDto.email())
                .cpf(usuarioDto.cpf())
                .login(usuarioDto.login())
                .senha(passwordEncoder.encode(usuarioDto.senha()))
                .roles(
                        usuarioDto.roles().stream()
                                .map(roleName -> Role.builder().name(roleName).build())
                                .toList()
                )
                .endereco(endereco)
                .dataUltimaAlteracao(LocalDate.now())
                .build();
    }

    public void alterarSenha(AlterarSenhaDTO dto) {
        UserDetailsImpl usuarioLogado = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Usuario usuario = usuarioLogado.getUsuario();


        if (!passwordEncoder.matches(dto.senhaAtual(), usuario.getSenha())) {
            throw new SenhaAtualIncorretaException();
        }


        usuario.setSenha(passwordEncoder.encode(dto.novaSenha()));
        usuario.setDataUltimaAlteracao(LocalDate.now());

        usuarioRepository.save(usuario);
    }

    public void atualizarUsuario(AtualizarUsuarioDTO dto) {

        UserDetailsImpl usuarioLogado = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Usuario usuario = usuarioLogado.getUsuario();

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setLogin(dto.login());
        usuario.setDataUltimaAlteracao(LocalDate.now());


        Endereco endereco = usuario.getEndereco();
        endereco.setRua(dto.endereco().rua());
        endereco.setNumero(dto.endereco().numero());
        endereco.setCidade(dto.endereco().cidade());
        endereco.setEstado(dto.endereco().estado());
        endereco.setCep(dto.endereco().cep());
        usuario.setEndereco(endereco);


        usuarioRepository.save(usuario);
    }

    public void deletarUsuario() {

        UserDetailsImpl usuarioLogado = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Usuario usuario = usuarioLogado.getUsuario();

        usuarioRepository.deleteById(usuario.getId());
    }
}




