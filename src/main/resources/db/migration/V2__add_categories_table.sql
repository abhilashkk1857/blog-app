CREATE TABLE categories (
  id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);


INSERT INTO categories (id, name)
VALUES ('00000000-0000-0000-0000-000000000000', 'General');


ALTER TABLE posts
ADD COLUMN category_id UUID;


UPDATE posts
SET category_id = '00000000-0000-0000-0000-000000000000'
WHERE posts.category_id IS NULL;


ALTER TABLE posts
Alter COLUMN category_id
SET NOT NULL ;


ALTER TABLE posts
    ADD Constraint fk_post_category
    FOREIGN KEY (category_id)
    REFERENCES  categories (id);