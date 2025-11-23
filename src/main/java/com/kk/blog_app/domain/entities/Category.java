package com.kk.blog_app.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @Column(nullable = false)
    List<Post> posts = new ArrayList<>();


}
