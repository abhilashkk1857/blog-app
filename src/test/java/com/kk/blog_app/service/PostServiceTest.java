package com.kk.blog_app.service;


import com.kk.blog_app.domain.entities.Category;
import com.kk.blog_app.domain.entities.Post;
import com.kk.blog_app.domain.entities.Role;
import com.kk.blog_app.domain.entities.User;
import com.kk.blog_app.exception.CategoryNotFoundException;
import com.kk.blog_app.exception.PostNotFoundException;
import com.kk.blog_app.repository.CategoryRepository;
import com.kk.blog_app.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PostRepository postRepository;


    @InjectMocks
    private PostService postService;


    @Test
    void shouldCreatePostWhenCategoryIsAvailable() {

        UUID categoryId = UUID.randomUUID();
        Category mockCategory = Category.builder().id(categoryId).name("Test").build();

        Post postInput = Post.builder().title("Title").category(mockCategory).build();


        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(mockCategory));

        when(postRepository.save(any(Post.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Post result = postService.createPost(postInput, categoryId);

        assertNotNull(result);
        assertEquals("Test", result.getCategory().getName());
        verify(postRepository, times(1)).save(any(Post.class));

    }

    @Test
    void shouldThrowErrorWhenCategoryDoesNotExists() {

        UUID fakeCategoryId = UUID.randomUUID();

        Post postInput = Post.builder().title("Title").build();

        when(categoryRepository.findById(fakeCategoryId)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> {
            postService.createPost(postInput, fakeCategoryId);
        });

        verify(postRepository, never()).save(any(Post.class));

    }

    @Test
    void shouldDeletePostWhenUserIsAuthor() {

        UUID userId = UUID.randomUUID();
        UUID postId = UUID.randomUUID();

        User user = User.builder().id(userId).role(Role.ROLE_USER).build();
        Post post = Post.builder().id(postId).title("Title").author(user).build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        postService.removePostById(postId, user);

        verify(postRepository, times(1)).delete(post);

    }



    @Test
    void shouldDeletePostWhenUserIsAdmin() {

        UUID userId = UUID.randomUUID();
        UUID postId = UUID.randomUUID();

        User author = User.builder().id(userId).role(Role.ROLE_USER).build();
        Post post = Post.builder().id(postId).title("Title").author(author).build();

        UUID adminId = UUID.randomUUID();
        User admin = User.builder().id(adminId).role(Role.ROLE_ADMIN).build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        postService.removePostById(postId, admin);

        verify(postRepository, times(1)).delete(post);

    }



    @Test
    void shouldThrowExceptionWhenUserIsNotAuthorOrAdmin() {

        UUID userId = UUID.randomUUID();
        UUID postId = UUID.randomUUID();

        User author = User.builder().id(userId).role(Role.ROLE_USER).build();
        Post post = Post.builder().id(postId).title("Title").author(author).build();

        UUID attackerId = UUID.randomUUID();
        User attacker = User.builder().id(attackerId).role(Role.ROLE_USER).build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));


        assertThrows(AccessDeniedException.class, () -> {
            postService.removePostById(postId, attacker);
        });

        verify(postRepository, never()).delete(any());

    }



    @Test
    void shouldThrowExceptionWhenPostIdIsInvalid() {

        UUID fakePostId = UUID.randomUUID();
        User user = User.builder().build();


        when(postRepository.findById(fakePostId)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> {
            postService.removePostById(fakePostId, user);
        });

        verify(postRepository, never()).delete(any());

    }

}
