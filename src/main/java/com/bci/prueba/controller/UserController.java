package com.bci.prueba.controller;

import com.bci.prueba.model.request.UserUpdateRequest;
import com.bci.prueba.model.response.UserResponse;
import com.bci.prueba.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Tag(name = "Api de Usuarios", description = "Permite listar, obtener por id, eliminar y actualizar")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Listar usuarios")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(UserResponse::new)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por id")
    public UserResponse getUserById(@PathVariable UUID id) {
        return new UserResponse(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Actualizar usuario", description = "Permite actualizar algunos valores de un usuario en particular")
    public void updateUser(@PathVariable UUID id, @Valid @RequestBody UserUpdateRequest request) {
        userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar usuario por id")
    public void deleteUserById(@PathVariable UUID id) {
        userService.deleteById(id);
    }

}
