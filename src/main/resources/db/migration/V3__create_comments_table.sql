CREATE TABLE comments (

    id UUID PRIMARY KEY,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    author_id UUID NOT NULL,
    post_id UUID NOT NULL,

    CONSTRAINT fk_comment_user
                      FOREIGN KEY (author_id)
                      REFERENCES users (id),

    CONSTRAINT fk_comment_post
                      FOREIGN KEY (post_id)
                      REFERENCES posts (id)
                      ON DELETE CASCADE

);