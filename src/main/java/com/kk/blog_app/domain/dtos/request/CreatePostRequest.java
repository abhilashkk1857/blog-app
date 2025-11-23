package com.kk.blog_app.domain.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreatePostRequest(

        @NotBlank(message = "title is required!")
        String title,

        @NotBlank(message = "content is required!")
        String content,

        @NotNull(message = "category ID is required!")
        UUID categoryId

) {
}
