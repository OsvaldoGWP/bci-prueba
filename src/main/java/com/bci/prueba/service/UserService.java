package com.bci.prueba.service;

import com.bci.prueba.model.User;
import com.bci.prueba.model.request.UserUpdateRequest;
import com.bci.prueba.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow();
    }

    public void updateUser(UUID id, UserUpdateRequest userUpdateRequest) {
        var updatedUser = userUpdateRequest.toEntity(getUserById(id));
        userRepository.save(updatedUser);
    }

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

}
