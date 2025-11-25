package com.kk.blog_app.controller;

import com.kk.blog_app.BaseIntegrationTest;
import com.kk.blog_app.domain.entities.*;
import com.kk.blog_app.repository.CategoryRepository;
import com.kk.blog_app.repository.CommentRepository;
import com.kk.blog_app.repository.PostRepository;
import com.kk.blog_app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentControllerTest extends BaseIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    void shouldDeleteCommentsWhenPostIsDeleted() {
        User user = userRepository.save(User.builder()
                .firstName("user")
                .lastName("name")
                .email("test@email.com")
                .role(Role.ROLE_USER)
                .password("password")
                .build()
        );

        Category category = categoryRepository.save(Category.builder().name("Test").build());

        Post post = postRepository.save(Post.builder()
                .title("Title")
                .content("content")
                .author(user)
                .category(category)
                .build()
        );

        Comment comment = commentRepository.save(Comment.builder()
                .content("comment")
                .post(post)
                .author(user)
                .build()
        );

        assertEquals(1, commentRepository.count());

        postRepository.delete(post);

        assertEquals(0, commentRepository.count());

    }

}
