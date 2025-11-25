package com.kk.blog_app.domain.mappers;

import com.kk.blog_app.domain.dtos.request.CreateCommentRequest;
import com.kk.blog_app.domain.dtos.response.CommentResponse;
import com.kk.blog_app.domain.entities.Comment;
import com.kk.blog_app.domain.entities.Post;
import com.kk.blog_app.domain.entities.User;
import org.springframework.stereotype.Component;


@Component
public class CommentMapper {

    public Comment toEntity(CreateCommentRequest request, User author, Post post) {
        return Comment.builder()
                .content(request.content())
                .author(author)
                .post(post)
                .build();
    }


    public CommentResponse toDto(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getPost().getId(),
                new CommentResponse.CommentAuthorDto(
                        comment.getAuthor().getId(),
                        comment.getAuthor().getFirstName(),
                        comment.getAuthor().getLastName()
                )
        );
    }

}
