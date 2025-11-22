package com.kk.blog_app.domain.dtos.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostResponse(
        UUID id,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        AuthorDto author
) {
    public record AuthorDto(
            UUID id,
            String firstName,
            String lastName
    ) {
    }
}
