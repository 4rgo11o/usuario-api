package io.github.usuarios.validations;

import io.github.usuarios.entities.enums.RoleName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class RoleNameValidator implements ConstraintValidator<ValidRoleNames, List<RoleName>> {

    @Override
    public boolean isValid(List<RoleName> roles, ConstraintValidatorContext context) {
        if (roles == null || roles.isEmpty()) return false;

        for (RoleName role : roles) {
            if (role == null) {
                return false;
            }
        }

        return true;
    }
}
