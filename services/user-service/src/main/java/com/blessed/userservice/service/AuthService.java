package com.blessed.userservice.service;


import com.blessed.userservice.config.JwtService;
import com.blessed.userservice.exception.UserNotFoundException;
import com.blessed.userservice.model.Role;
import com.blessed.userservice.model.UserEntity;
import com.blessed.userservice.model.dto.UserCreateRequest;
import com.blessed.userservice.model.dto.UserResponse;
import com.blessed.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    // Регистрация
    public UserResponse register(UserCreateRequest request) {
        if (userService.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User with email " + request.getEmail() + " already exists");
        }

        UserEntity user = UserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .role(Role.USER)
                .build();

        UserEntity saved = userRepository.save(user);
        return new UserResponse(saved.getId(), saved.getEmail(), saved.getName());
    }

    // Логин
    public String authenticate(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        UserEntity user = userRepository.findByEmail(email);
        if (user!=null) {
            return jwtService.generateToken(user);
        }
        else {
            throw new UserNotFoundException(email);
        }
    }
}
