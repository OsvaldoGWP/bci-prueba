package com.bci.prueba.service;

import com.bci.prueba.errorhandling.GenericException;
import com.bci.prueba.model.User;
import com.bci.prueba.repository.UserRepository;
import com.bci.prueba.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.bci.prueba.errorhandling.ErrorCode.EMAIL_ALREADY_EXISTS;
import static com.bci.prueba.errorhandling.ErrorCode.INVALID_FORMAT_PASSWORD;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Value("${app.regexp.password}")
    private String passwordRegexp;

    public User register(User user) {
        if (!user.getPassword().matches(passwordRegexp))
            throw new GenericException(INVALID_FORMAT_PASSWORD, 400);
        if (userRepository.findByEmailIgnoreCase(user.getEmail()).isPresent())
            throw new GenericException(EMAIL_ALREADY_EXISTS, 409);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var jwt = jwtService.generateToken(user);
        user.setToken(jwt);
        user.setActive(true);
        return userRepository.save(user);
    }

    public User login(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        var user = userRepository.findByEmailIgnoreCase(email).orElseThrow();
        user.setToken(jwtService.generateToken(user));
        user.setLastLogin(LocalDateTime.now());
        return userRepository.save(user);
    }

}
