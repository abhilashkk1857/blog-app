package com.kk.blog_app.domain.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record UserSignInRequest(

        @NotBlank(message = "email is required!")
        String email,

        @NotBlank(message = "password is required!")
        String password
) {
}
