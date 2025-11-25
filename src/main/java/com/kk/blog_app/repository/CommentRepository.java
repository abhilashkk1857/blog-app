package com.kk.blog_app.repository;

import com.kk.blog_app.domain.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Page<Comment> findByPostId(UUID id, Pageable pageable);
}
