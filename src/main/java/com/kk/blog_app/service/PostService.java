package com.kk.blog_app.service;

import com.kk.blog_app.domain.dtos.request.UpdatePostRequest;
import com.kk.blog_app.domain.entities.Post;
import com.kk.blog_app.domain.entities.Role;
import com.kk.blog_app.domain.entities.User;
import com.kk.blog_app.exception.PostNotFoundException;
import com.kk.blog_app.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }


    public Post getPostById(UUID id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with the id: " + id));
    }


    public Post createPost(Post post) {
        return postRepository.save(post);
    }


    public Post updatePostByID(UUID id, UpdatePostRequest request, User user) {

        Post post = postRepository.findById(id).
                orElseThrow(() -> new PostNotFoundException("Post not found with the id: " + id));

        if ( !post.getAuthor().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not allowed to update this post!");
        }

        post.setTitle(request.title());
        post.setContent(request.content());

        System.out.println(request.title() + " " + request.content());

        return postRepository.save(post);

    }


    @Transactional
    public void removePostById(UUID id, User user) {
        Post post = postRepository.findById(id).
                orElseThrow(() -> new PostNotFoundException("Post not found with the id: " + id));

        boolean isAuthor = post.getAuthor().getId().equals(user.getId());

        boolean isAdmin = user.getRole().equals(Role.ROLE_ADMIN);

        if (!isAuthor && !isAdmin) {
            throw new AccessDeniedException("You are not allowed to remove this post!");
        }

        postRepository.delete(post);
    }
}
