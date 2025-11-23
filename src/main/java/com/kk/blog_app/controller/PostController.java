package com.kk.blog_app.controller;


import com.kk.blog_app.domain.dtos.request.CreatePostRequest;
import com.kk.blog_app.domain.dtos.request.UpdatePostRequest;
import com.kk.blog_app.domain.dtos.response.PostResponse;
import com.kk.blog_app.domain.entities.Post;
import com.kk.blog_app.domain.entities.User;
import com.kk.blog_app.domain.mappers.PostMapper;
import com.kk.blog_app.service.PostService;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @Valid @RequestBody CreatePostRequest request,
            Authentication authentication
            ) {

        User user = (User) authentication.getPrincipal();
        Post post = postMapper.toEntity(request, user);

        Post createdPost = postService.createPost(post);

        return ResponseEntity.status(HttpStatus.CREATED).body(postMapper.toDto(createdPost));

    }


    @GetMapping
    public ResponseEntity<Page<PostResponse>> getAllPosts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "createdAt") String sortBy,
        @RequestParam(defaultValue = "desc") String sortDir
    ){

        Sort.Direction direction = sortDir.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;


        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Post> postPage = postService.getAllPosts(pageable);

        Page<PostResponse> response = postPage.map(postMapper::toDto);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable UUID id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(postMapper.toDto(post));
    }


    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePostById(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePostRequest request,
            Authentication authentication
            ) {

        User user = (User) authentication.getPrincipal();

        Post updatedPost = postService.updatePostByID(id, request, user);

        return ResponseEntity.ok(postMapper.toDto(updatedPost));

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable UUID id, Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        postService.removePostById(id, user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
