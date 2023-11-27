package com.bci.prueba.model.response;

import com.bci.prueba.model.Phone;
import com.bci.prueba.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Getter
@Setter
public class UserResponse {

    private UUID id;
    private String name;
    private String email;
    private LocalDateTime lastLogin;
    private String token;
    private boolean isActive;
    private List<Phone> phones;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public UserResponse(User user) {
        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        lastLogin = ofNullable(user.getLastLogin()).orElse(user.getCreatedDate());
        token = user.getToken();
        isActive = user.isActive();
        phones = user.getPhones();
        createdDate = user.getCreatedDate();
        modifiedDate = user.getModifiedDate();
    }

}
