package com.kk.blog_app.domain.dtos.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentResponse(
        UUID id,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        UUID postId,
        CommentAuthorDto author
) {

   public record CommentAuthorDto(
           UUID id,
           String firstName,
           String lastName
   ) {}

}
