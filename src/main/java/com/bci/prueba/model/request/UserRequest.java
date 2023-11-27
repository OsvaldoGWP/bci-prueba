package com.bci.prueba.model.request;

import com.bci.prueba.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class UserRequest {

    @NotBlank
    @Schema(description = "Nombre del usuario", example = "Osvaldo Perez")
    private String name;
    @Email
    @NotBlank
    @Schema(description = "Email del usuario con formato correcto", example = "osvaldo@gmail.com")
    private String email;
    @NotBlank
    @Schema(description = "Contraseña que debe tener 4 a 8 caracteres y debe contener números, letras minúsculas y mayúsculas", example = "Messi23")
    private String password;
    @Valid
    @NotNull
    @Schema(description = "Teléfonos del usuario")
    private List<PhoneRequest> phones;

    public User toEntity() {
        var user = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
        user.setPhones(phones.stream().map(phoneRequest -> phoneRequest.toEntity(user)).toList());
        return user;
    }

}
