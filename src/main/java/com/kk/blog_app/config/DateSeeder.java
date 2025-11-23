package com.kk.blog_app.config;

import com.kk.blog_app.domain.entities.Post;
import com.kk.blog_app.domain.entities.User;
import com.kk.blog_app.repository.PostRepository;
import com.kk.blog_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Profile("dev")
@Component
@RequiredArgsConstructor
public class DateSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {

        if (postRepository.count() > 10) {
            System.out.println("Posts already seeded! Skipped.........");
            return;
        }

        User author = userRepository.findAll().stream()
                .findFirst()
                .orElse(null);

        if(author == null) {
            System.out.println("No users found in DB. Cannot seed posts. Register a user first!");
            return;
        }


        System.out.println("Seeding 50 dummy posts...");
        List<Post> posts = new ArrayList<>();

        for (int i=1; i<=50; i++) {
            Post post = Post.builder()
                    .title("Seeded Post Title #" + i)
                    .content("This is the dummy content for post number " + i + ". " +
                            "We are generating this to test pagination and sorting features " +
                            "in our Spring Boot Blog Application.")
                    .author(author)
                    .build();
            posts.add(post);
        }


        postRepository.saveAll(posts);
        System.out.println("Success! 50 posts added to the database.");

    }
}
