package com.kk.blog_app.config;

import com.kk.blog_app.domain.entities.Category;
import com.kk.blog_app.domain.entities.Post;
import com.kk.blog_app.domain.entities.User;
import com.kk.blog_app.repository.CategoryRepository;
import com.kk.blog_app.repository.PostRepository;
import com.kk.blog_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Profile("dev")
@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {

        if (postRepository.count() > 10) {
            log.info("Posts already seeded! Skipped.........");
            return;
        }

        User author = userRepository.findAll().stream()
                .findFirst()
                .orElse(null);

        if(author == null) {
            log.info("No users found in DB. Cannot seed posts. Register a user first!");
            return;
        }

        Category generalCategory = categoryRepository.findAll().stream()
                        .filter(c -> c.getName().equals("General"))
                        .findFirst()
                                .orElse(null);

        if (generalCategory == null) {
            log.info("General Category not found. Skipping seeding......");
            return;
        }


        log.info("Seeding 50 dummy posts...");
        List<Post> posts = new ArrayList<>();

        for (int i=1; i<=50; i++) {
            Post post = Post.builder()
                    .title("Seeded Post Title #" + i)
                    .content("This is the dummy content for post number " + i + ". " +
                            "We are generating this to test pagination and sorting features " +
                            "in our Spring Boot Blog Application.")
                    .author(author)
                    .category(generalCategory)
                    .build();
            posts.add(post);
        }


        postRepository.saveAll(posts);
        log.info("Success! 50 posts added to the database.");

    }
}
