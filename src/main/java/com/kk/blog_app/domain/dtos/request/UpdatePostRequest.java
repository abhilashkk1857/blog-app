package com.kk.blog_app.domain.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record UpdatePostRequest(

        @NotBlank(message = "title is required!")
        String title,

        @NotBlank(message = "content is required!")
        String content
) {
}
