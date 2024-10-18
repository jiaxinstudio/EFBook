CREATE TABLE IF NOT EXISTS Book
(
    id
    BIGINT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    title
    VARCHAR
(
    255
) NOT NULL,
    author VARCHAR
(
    255
) NOT NULL,
    isbn VARCHAR
(
    255
) NOT NULL,
    cover_url VARCHAR
(
    255
),
    price DECIMAL
(
    10,
    2
) NOT NULL
    );

INSERT INTO Book (title, author, isbn, cover_url, price)
VALUES ('Clean Code', 'Robert C. Martin', '9780132350884', 'http://example.com/cleancode.jpg', 35.99),
       ('The Pragmatic Programmer', 'Andrew Hunt', '9780201616224', 'http://example.com/pragmatic.jpg', 42.50),
       ('Effective Java', 'Joshua Bloch', '9780134685991', 'http://example.com/effectivejava.jpg', 45.00);

CREATE TABLE IF NOT EXISTS Inventory
(
    id
    BIGINT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    book_id
    BIGINT
    NOT
    NULL,
    quantity
    INT
    NOT
    NULL,
    FOREIGN
    KEY
(
    book_id
) REFERENCES Book
(
    id
)
    );


INSERT INTO Inventory (book_id, quantity)
VALUES (1, 100),
       (2, 50),
       (3, 75);