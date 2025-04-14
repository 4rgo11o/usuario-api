package io.github.usuarios.controllers.handlers;

import io.github.usuarios.dto.ResponseStatusDTO;
import io.github.usuarios.dto.ValidationErrorDTO;
import io.github.usuarios.exception.FailureLoginException;
import io.github.usuarios.exception.SenhaAtualIncorretaException;
import io.github.usuarios.exception.TokenAusenteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



import java.util.Collections;
import java.util.List;

import java.util.stream.Collectors;



@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDTO> handlerValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ValidationErrorDTO validationErrorDTO = new ValidationErrorDTO(errors, HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(validationErrorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ValidationErrorDTO> handlerIllegalArgumentException(IllegalArgumentException ex) {
        String message = ex.getMessage();


        if (message != null && message.contains("RoleName")) {
            message = "Por favor, cadastre-se como CLIENTE ou PROPRIETARIO.";
        }

        List<String> errors = Collections.singletonList(message);
        ValidationErrorDTO validationErrorDTO = new ValidationErrorDTO(errors, HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(validationErrorDTO, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(FailureLoginException.class)
    public ResponseEntity<ResponseStatusDTO> handlerFailureLoginException(FailureLoginException ex) {
        var status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status.value()).body(new ResponseStatusDTO(ex.getMessage(), status.value()));
    }

    @ExceptionHandler(SenhaAtualIncorretaException.class)
    public ResponseEntity<ResponseStatusDTO> handlerSenhaAtualIncorreta(SenhaAtualIncorretaException ex) {
        var status = HttpStatus.BAD_REQUEST;
        return ResponseEntity
                .status(status)
                .body(new ResponseStatusDTO(ex.getMessage(), status.value()));
    }
}



