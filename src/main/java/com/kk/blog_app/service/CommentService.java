package com.kk.blog_app.service;

import com.kk.blog_app.domain.entities.Comment;
import com.kk.blog_app.domain.entities.Post;
import com.kk.blog_app.domain.entities.Role;
import com.kk.blog_app.domain.entities.User;
import com.kk.blog_app.exception.CommentNotFoundException;
import com.kk.blog_app.exception.PostNotFoundException;
import com.kk.blog_app.repository.CommentRepository;
import com.kk.blog_app.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public Comment createComment(UUID postId, String content, User user) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post Not found with the id: " + postId));

        Comment comment = Comment.builder()
                .post(post)
                .content(content)
                .author(user)
                .build();

        return commentRepository.save(comment);

    }

    public void removeCommentById(UUID commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with the id: " + commentId));

        boolean isCommentOwner = comment.getAuthor().getId().equals(user.getId());

        boolean isPostAuthor = comment.getPost().getAuthor().getId().equals(user.getId());

        boolean isAdmin = user.getRole().equals(Role.ROLE_ADMIN);

        if(!isCommentOwner && !isPostAuthor && !isAdmin) {
            throw new AccessDeniedException("You are not allowed to remove this comment!");
        }

        commentRepository.delete(comment);
    }

    public Page<Comment> getCommentsByPostId(UUID postId, Pageable pageable) {

        return commentRepository.findByPostId(postId, pageable);

    }
}
