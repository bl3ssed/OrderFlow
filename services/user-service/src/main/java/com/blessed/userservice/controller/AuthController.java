package com.blessed.userservice.controller;

import com.blessed.userservice.model.dto.UserCreateRequest;
import com.blessed.userservice.model.dto.UserLoginRequest;
import com.blessed.userservice.model.dto.UserResponse;
import com.blessed.userservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        UserResponse response = authService.register(userCreateRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        String token = authService.authenticate(userLoginRequest.getEmail(), userLoginRequest.getPassword());
        return ResponseEntity.ok(token);
    }
}
