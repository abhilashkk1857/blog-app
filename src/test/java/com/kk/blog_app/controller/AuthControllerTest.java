package com.kk.blog_app.controller;

import com.kk.blog_app.BaseIntegrationTest;
import com.kk.blog_app.domain.dtos.request.UserSignUpRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class AuthControllerTest extends BaseIntegrationTest {

    @Test
    void shouldSignUpUserSuccessfully() {

        UserSignUpRequest request = new UserSignUpRequest(
                "test", "user", "test@example.com", "password"
        );


        given()
                .contentType(ContentType.JSON)
                .body(request)
        .when()
                .post("/api/v1/auth/sign-up")

        .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("token", notNullValue());

    }


    @Test
    void shouldFailIfEmailAlreadyExists() {

        UserSignUpRequest request = new UserSignUpRequest(
                "duplicate", "test", "duplicate@example.com", "password"
        );

        given().contentType(ContentType.JSON).body(request)
                .when().post("api/v1/auth/sign-up")
                .then().statusCode(HttpStatus.CREATED.value()).body("token", notNullValue());



        given().contentType(ContentType.JSON).body(request)
                .when().post("api/v1/auth/sign-up")
                .then().statusCode(HttpStatus.CONFLICT.value());

    }

}
