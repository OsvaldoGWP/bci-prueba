package com.bci.prueba.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_FORMAT_PASSWORD("La contraseña debe tener 4 a 8 caracteres y debe contener números, letras minúsculas y mayúsculas."),
    EMAIL_ALREADY_EXISTS("El email ya fue registrado.");

    private final String message;
}
