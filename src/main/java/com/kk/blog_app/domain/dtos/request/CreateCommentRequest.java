package com.kk.blog_app.domain.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCommentRequest(
        @NotBlank(message = "content is required!")
        String content
) {}
