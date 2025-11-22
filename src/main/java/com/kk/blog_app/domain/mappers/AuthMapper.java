package com.kk.blog_app.domain.mappers;

import com.kk.blog_app.domain.dtos.request.UserSignInRequest;
import com.kk.blog_app.domain.dtos.request.UserSignUpRequest;
import com.kk.blog_app.domain.dtos.response.AuthResponse;
import com.kk.blog_app.domain.entities.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {


    public User toEntity(UserSignUpRequest request) {
        return User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(request.password())
                .build();
    }



    public AuthResponse toDto(String token) {
        return new AuthResponse(token);
    }

}
