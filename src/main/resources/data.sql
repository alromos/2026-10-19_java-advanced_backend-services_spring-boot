INSERT INTO roles (name) VALUES ('USER');
INSERT INTO roles (name) VALUES ('ADMIN');

-- passwords are BCrypt-encoded, password matches username)
INSERT INTO users (username, password) VALUES ('user',  '$2a$10$UOWAFFpLgp5jSBJbitvRp.whRst9vjXbmN0kqNMVkxWsvZ1myB8bC');
INSERT INTO users (username, password) VALUES ('admin', '$2a$10$ik6QkUa/LF/Aa2NOb2KTCegYpCDz8zS7RXsK/5kxj/YFyoTU2FLdK');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);

INSERT INTO authors (first_name, last_name) VALUES ('J.R.R.',  'Tolkien');
INSERT INTO authors (first_name, last_name) VALUES ('J.K.',    'Rowling');
INSERT INTO authors (first_name, last_name) VALUES ('George',  'Orwell');
INSERT INTO authors (first_name, last_name) VALUES ('Isaac',   'Asimov');
INSERT INTO authors (first_name, last_name) VALUES ('Terry',   'Pratchett');
INSERT INTO authors (first_name, last_name) VALUES ('Neil',    'Gaiman');

INSERT INTO books (name) VALUES ('The Lord of the Rings');
INSERT INTO books (name) VALUES ('Harry Potter and the Philosopher''s Stone');
INSERT INTO books (name) VALUES ('1984');
INSERT INTO books (name) VALUES ('Foundation');
INSERT INTO books (name) VALUES ('Good Omens');

INSERT INTO book_authors (book_id, author_id) VALUES (1, 1);
INSERT INTO book_authors (book_id, author_id) VALUES (2, 2);
INSERT INTO book_authors (book_id, author_id) VALUES (3, 3);
INSERT INTO book_authors (book_id, author_id) VALUES (4, 4);
INSERT INTO book_authors (book_id, author_id) VALUES (5, 5);
INSERT INTO book_authors (book_id, author_id) VALUES (5, 6);

INSERT INTO book_genres (book_id, genre) VALUES (1, 'FANTASY');
INSERT INTO book_genres (book_id, genre) VALUES (1, 'FICTION');
INSERT INTO book_genres (book_id, genre) VALUES (2, 'FANTASY');
INSERT INTO book_genres (book_id, genre) VALUES (2, 'FICTION');
INSERT INTO book_genres (book_id, genre) VALUES (3, 'FICTION');
INSERT INTO book_genres (book_id, genre) VALUES (3, 'SCIENCE');
INSERT INTO book_genres (book_id, genre) VALUES (4, 'SCIENCE');
INSERT INTO book_genres (book_id, genre) VALUES (4, 'FICTION');
INSERT INTO book_genres (book_id, genre) VALUES (5, 'FANTASY');
INSERT INTO book_genres (book_id, genre) VALUES (5, 'FICTION');
INSERT INTO book_genres (book_id, genre) VALUES (5, 'MYSTERY');
