CREATE TABLE authors (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         bio VARCHAR(255)
);

CREATE TABLE genres (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL
);

CREATE TABLE books (
                       id SERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       description VARCHAR(255),
                       published_date DATE,
                       isbn VARCHAR(13)
);

CREATE TABLE book_author (
                             book_id INT REFERENCES books(id) ON DELETE CASCADE,
                             author_id INT REFERENCES authors(id) ON DELETE CASCADE,
                             PRIMARY KEY (book_id, author_id)
);

CREATE TABLE book_genre (
                            book_id INT REFERENCES books(id) ON DELETE CASCADE,
                            genre_id INT REFERENCES genres(id) ON DELETE CASCADE,
                            PRIMARY KEY (book_id, genre_id)
);

CREATE INDEX idx_book_title ON books(title);
CREATE INDEX idx_author_name ON authors(name);
CREATE INDEX idx_genre_name ON genres(name);
