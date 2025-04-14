package io.github.usuarios.validations;


import io.github.usuarios.entities.Usuario;
import io.github.usuarios.repository.UsuarioRepository;
import io.github.usuarios.security.UserDetailsImpl;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isBlank()) return true;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof UserDetailsImpl userDetails)) {

            return !usuarioRepository.existsByEmail(email);
        }

        Usuario usuarioLogado = userDetails.getUsuario();

        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(email);


        return usuarioExistente.isEmpty() || usuarioExistente.get().getId().equals(usuarioLogado.getId());
    }
}

