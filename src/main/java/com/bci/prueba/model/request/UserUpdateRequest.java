package com.bci.prueba.model.request;

import com.bci.prueba.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class UserUpdateRequest {

    @NotBlank
    private String name;
    @Valid
    @NotNull
    private List<PhoneRequest> phones;

    public User toEntity(User user) {
        user.setName(name);
        user.setPhones(phones.stream().map(phoneRequest -> phoneRequest.toEntity(user)).toList());
        return user;
    }

}
