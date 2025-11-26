package com.kk.blog_app.domain.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserSignUpRequest(
        @NotBlank(message = "FirstName is required!")
        String firstName,

        @NotBlank(message = "LastName is required!")
        String lastName,

        @NotBlank(message = "email is required!")
        @Email(message = "Invalid email format!")
        String email,

        @NotBlank(message = "password is required!")
        @Size(min = 8, message = "password must be of at least 8 characters!")
        String password
) {
}
