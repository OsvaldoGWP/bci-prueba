package com.bci.prueba.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginRequest {

    @NotBlank
    @Schema(description = "Email del usuario", example = "osvaldo@gmail.com")
    private String email;
    @NotBlank
    @Schema(description = "Contraseña del usuario", example = "Messi23")
    private String password;
}
