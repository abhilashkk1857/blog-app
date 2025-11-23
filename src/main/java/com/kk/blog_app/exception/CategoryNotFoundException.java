package com.kk.blog_app.exception;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String messsage) {
        super(messsage);
    }
}
