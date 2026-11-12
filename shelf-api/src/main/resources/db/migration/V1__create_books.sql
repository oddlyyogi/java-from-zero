CREATE TABLE book (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    isbn VARCHAR(13) NOT NULL UNIQUE,
    title VARCHAR(200) NOT NULL,
    author VARCHAR(200) NOT NULL,
    published_year INTEGER NOT NULL
);

INSERT INTO book (isbn, title, author, published_year)
VALUES ('9780441013593', 'Dune', 'Frank Herbert', 1965),
       ('9780141439587', 'Emma', 'Jane Austen', 1815);
