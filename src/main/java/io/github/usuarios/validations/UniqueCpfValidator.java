package io.github.usuarios.validations;


import io.github.usuarios.repository.UsuarioRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueCpfValidator implements ConstraintValidator<UniqueCpf, String> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        return cpf != null && usuarioRepository.findByCpf(cpf).isEmpty();
    }
}