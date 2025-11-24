package com.kk.blog_app.controller;

import com.kk.blog_app.BaseIntegrationTest;
import com.kk.blog_app.domain.dtos.request.CreatePostRequest;
import com.kk.blog_app.domain.dtos.request.UserSignInRequest;
import com.kk.blog_app.domain.dtos.request.UserSignUpRequest;
import com.kk.blog_app.domain.entities.Category;
import com.kk.blog_app.domain.entities.Post;
import com.kk.blog_app.domain.entities.Role;
import com.kk.blog_app.domain.entities.User;
import com.kk.blog_app.repository.CategoryRepository;
import com.kk.blog_app.repository.PostRepository;
import com.kk.blog_app.repository.UserRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostControllerTest extends BaseIntegrationTest {


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    private Category defaultCategory;

    @BeforeEach
    void setUpCategory() {
        defaultCategory = categoryRepository.findByName("General")
                .orElseGet(() -> categoryRepository.save(Category.builder().name("General").build()));
    }


    @Test
    void shouldCreatePostSuccessfully() {

        String token = authenticateAndGetToken("creator@email.com", "password");

        CreatePostRequest createPostRequest = new CreatePostRequest("Integration Test Title", "Integration Test Content", defaultCategory.getId());

        given()
                .contentType(ContentType.JSON).header("Authorization", "Bearer "+ token).body(createPostRequest)
                .when()
                    .post("/api/v1/posts")
                .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("title", equalTo("Integration Test Title"))
                    .body("category.name", equalTo("General"));


    }


    @Test
    void shouldFailToDeleteOthersPost() {

        User author = saveUser("author@email.com", "password");

        Post post = postRepository.save(
                Post.builder()
                .title("Title")
                .content("Content")
                .category(defaultCategory)
                .author(author)
                .build());


        String attackerToken = authenticateAndGetToken("attacker@email.com", "attacker");


        given()
                .header("Authorization", "Bearer " + attackerToken)
                .when()
                    .delete("/api/v1/posts/" + post.getId())
                .then()
                    .statusCode(HttpStatus.UNAUTHORIZED.value());


    }

    private String authenticateAndGetToken(String email, String password) {

        UserSignUpRequest signUpRequest = new UserSignUpRequest("firstname", "lastname", email, password);

        given().contentType(ContentType.JSON).body(signUpRequest).when().post("/api/v1/auth/sign-up");

        UserSignInRequest signInRequest = new UserSignInRequest(email, password);

        return given().contentType(ContentType.JSON).body(signInRequest)
                .when().post("/api/v1/auth/sign-in")
                .then().extract().path("token");

    }

    private User saveUser(String email, String password) {
        return userRepository.save(
                User.builder()
                        .firstName("test")
                        .lastName("test")
                        .email(email)
                        .password(passwordEncoder.encode(password))
                        .role(Role.ROLE_USER)
                        .build()
        );
    }

}
