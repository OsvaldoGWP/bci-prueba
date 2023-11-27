package com.bci.prueba.service;

import com.bci.prueba.model.User;
import com.bci.prueba.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void loadUserByUsername() {
        var userMock = getUserMock();
        when(userRepository.findByEmailIgnoreCase(anyString()))
                .thenReturn(Optional.of(userMock));

        var result = userService.loadUserByUsername("username");

        assertThat(result.getUsername()).isEqualTo(userMock.getEmail());
        assertThat(result.getPassword()).isEqualTo(userMock.getPassword());
    }

    @Test
    void loadUserByUsername_expect_UsernameNotFoundException() {
        var userMock = getUserMock();
        when(userRepository.findByEmailIgnoreCase(anyString()))
                .thenReturn(Optional.empty());

        var result = assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("username"));

        assertThat(result.getMessage()).isEqualTo("Usuario no encontrado");
    }

    @Test
    void getUserById() {
        var userMock = getUserMock();
        when(userRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(userMock));

        var result = userService.getUserById(UUID.randomUUID());

        assertThat(result).isNotNull();
    }

    private User getUserMock() {
        return User.builder()
                .name("name")
                .email("osvaldo@gmail.com")
                .password("******")
                .build();
    }
}