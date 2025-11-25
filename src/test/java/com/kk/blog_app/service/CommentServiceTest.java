package com.kk.blog_app.service;


import com.kk.blog_app.domain.entities.Comment;
import com.kk.blog_app.domain.entities.Post;
import com.kk.blog_app.domain.entities.Role;
import com.kk.blog_app.domain.entities.User;
import com.kk.blog_app.repository.CommentRepository;
import com.kk.blog_app.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;


    @InjectMocks
    private CommentService commentService;


    @Test
    void shouldDeleteCommentWhenUserIsCommentOwner() {

        UUID commentId = UUID.randomUUID();

        User commentOwner = User.builder().id(UUID.randomUUID()).role(Role.ROLE_USER).build();
        User postAuthor = User.builder().id(UUID.randomUUID()).role(Role.ROLE_USER).build();
        Post post = Post.builder().id(UUID.randomUUID()).author(postAuthor).build();
        Comment comment = Comment.builder().id(commentId).post(post).author(commentOwner).build();


        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        commentService.removeCommentById(commentId, commentOwner);

        verify(commentRepository).delete(comment);


    }


    @Test
    void shouldDeleteCommentWhenUserIsPostAuthor() {

        UUID commentId = UUID.randomUUID();

        User postAuthor = User.builder().id(UUID.randomUUID()).role(Role.ROLE_USER).build();
        User commentOwner = User.builder().id(UUID.randomUUID()).role(Role.ROLE_USER).build();
        Post post = Post.builder().id(UUID.randomUUID()).author(postAuthor).build();

        Comment comment = Comment.builder().id(commentId).author(commentOwner).post(post).build();


        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        commentService.removeCommentById(commentId, postAuthor);

        verify(commentRepository).delete(comment);

    }


    @Test
    void shouldDeleteCommentWhenUserIsAdmin() {

        UUID commentId = UUID.randomUUID();

        User postAuthor = User.builder().id(UUID.randomUUID()).role(Role.ROLE_USER).build();
        User commentOwner = User.builder().id(UUID.randomUUID()).role(Role.ROLE_USER).build();
        Post post = Post.builder().id(UUID.randomUUID()).author(postAuthor).build();

        Comment comment = Comment.builder().id(commentId).author(commentOwner).post(post).build();

        User admin = User.builder().id(UUID.randomUUID()).role(Role.ROLE_ADMIN).build();

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        commentService.removeCommentById(commentId, admin);

        verify(commentRepository).delete(comment);

    }


    @Test
    void ShouldThrowErrorWhenUserIsStranger() {

        UUID commentId = UUID.randomUUID();

        User postAuthor = User.builder().id(UUID.randomUUID()).role(Role.ROLE_USER).build();
        User commentOwner = User.builder().id(UUID.randomUUID()).role(Role.ROLE_USER).build();
        Post post = Post.builder().id(UUID.randomUUID()).author(postAuthor).build();

        Comment comment = Comment.builder().id(commentId).author(commentOwner).post(post).build();

        User attacker = User.builder().id(UUID.randomUUID()).role(Role.ROLE_USER).build();

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        assertThrows(AccessDeniedException.class, () -> {
            commentService.removeCommentById(commentId, attacker);
        });

        verify(commentRepository, never()).delete(comment);

    }


}
