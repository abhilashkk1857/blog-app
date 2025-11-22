package com.kk.blog_app.domain.mappers;

import com.kk.blog_app.domain.dtos.request.CreatePostRequest;
import com.kk.blog_app.domain.dtos.response.PostResponse;
import com.kk.blog_app.domain.entities.Post;
import com.kk.blog_app.domain.entities.User;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public Post toEntity (CreatePostRequest request, User author) {
        return Post.builder()
                .title(request.title())
                .content(request.content())
                .author(author)
                .build();
    }


    public PostResponse toDto(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                new PostResponse.AuthorDto(
                        post.getAuthor().getId(),
                        post.getAuthor().getFirstName(),
                        post.getAuthor().getLastName()
                )
        );
    }

}
