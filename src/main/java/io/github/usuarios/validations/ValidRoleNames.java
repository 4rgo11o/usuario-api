package io.github.usuarios.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RoleNameValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRoleNames {
    String message() default "Contém perfil inválido. Use: cliente e/ou proprietario";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
