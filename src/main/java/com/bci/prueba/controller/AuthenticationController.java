package com.bci.prueba.controller;

import com.bci.prueba.model.request.LoginRequest;
import com.bci.prueba.model.request.UserRequest;
import com.bci.prueba.model.response.UserResponse;
import com.bci.prueba.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Registro y Login", description = "API para el registro de usuarios y el login, en caso exitoso se devuelve un token JWT")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar un usuario", description = "Permite el registro de un usuario siempre y cuando no se haya registrado un usuario con el email ingresado y que se respete el formato de la contraseña")
    public UserResponse register(@Valid @RequestBody UserRequest request) {
        var registeredUser = authenticationService.register(request.toEntity());
        return new UserResponse(registeredUser);
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar Sesión", description = "Permite iniciar sesión con usuario y contraseña si es correcto nos devolverá dentro de la respuesta un token JWT. Nota: no se debe mandar el token en el header al realizar login")
    public UserResponse login(@Valid @RequestBody LoginRequest request) {
        var user = authenticationService.login(request.getEmail(), request.getPassword());
        return new UserResponse(user);
    }

}
