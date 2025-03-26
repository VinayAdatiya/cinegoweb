CREATE TABLE seat_category
(
    seat_category_id INT PRIMARY KEY AUTO_INCREMENT,
    seat_type        VARCHAR(255) NOT NULL UNIQUE
);

INSERT INTO seat_category (seat_type)
VALUES ('InActive'),
       ('Regular'),
       ('Premium'),
       ('VIP'),
       ('Recliner'),
       ('Sofa');

CREATE TABLE seats
(
    seat_id          INT PRIMARY KEY AUTO_INCREMENT,
    screen_id        INT,
    row_num          INT NOT NULL,
    col_num          INT NOT NULL,
    seat_category_id INT NOT NULL,
    FOREIGN KEY (screen_id) REFERENCES screen (screen_id),
    FOREIGN KEY (seat_category_id) REFERENCES seat_category (seat_category_id)
);