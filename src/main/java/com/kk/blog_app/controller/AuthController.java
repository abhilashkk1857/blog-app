package com.kk.blog_app.controller;

import com.kk.blog_app.domain.dtos.request.UserSignInRequest;
import com.kk.blog_app.domain.dtos.request.UserSignUpRequest;
import com.kk.blog_app.domain.dtos.response.AuthResponse;
import com.kk.blog_app.domain.entities.User;
import com.kk.blog_app.domain.mappers.AuthMapper;
import com.kk.blog_app.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthMapper authMapper;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> signUp(@Valid @RequestBody UserSignUpRequest request) {
        User user = authMapper.toEntity(request);
        AuthResponse response = authMapper.toDto(authService.signUpUser(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> signIn(@Valid @RequestBody UserSignInRequest request) {
        String token = authService.signInUser(request.email(), request.password());
        AuthResponse response = authMapper.toDto(token);
        return ResponseEntity.ok(response);
    }

}
