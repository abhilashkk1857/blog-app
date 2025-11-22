package com.kk.blog_app.domain.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record UserSignUpRequest(
        @NotBlank(message = "FirstName is required!")
        String firstName,

        @NotBlank(message = "LastName is required!")
        String lastName,

        @NotBlank(message = "email is required!")
        String email,

        @NotBlank(message = "password is required!")
        String password
) {
}
