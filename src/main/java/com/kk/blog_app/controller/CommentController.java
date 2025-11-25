package com.kk.blog_app.controller;

import com.kk.blog_app.domain.dtos.request.CreateCommentRequest;
import com.kk.blog_app.domain.dtos.response.CommentResponse;
import com.kk.blog_app.domain.entities.Comment;
import com.kk.blog_app.domain.entities.User;
import com.kk.blog_app.domain.mappers.CommentMapper;
import com.kk.blog_app.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable UUID postId,
            @Valid @RequestBody CreateCommentRequest request,
            Authentication authentication
            ) {

        User user = (User) authentication.getPrincipal();

        Comment comment = commentService.createComment(postId, request.content(), user);

        CommentResponse response = commentMapper.toDto(comment);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }


    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<Page<CommentResponse>> getComments(
            @PathVariable UUID postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<Comment> commentPage = commentService.getCommentsByPostId(postId, pageable);

        return ResponseEntity.ok(commentPage.map(commentMapper::toDto));

    }


    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> removeCommentById(
            @PathVariable UUID commentId,
            Authentication authentication
    ) {

        User user = (User) authentication.getPrincipal();

        commentService.removeCommentById(commentId, user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
