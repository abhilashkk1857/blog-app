package com.kk.blog_app.domain.dtos.response;

import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name
) {
}
