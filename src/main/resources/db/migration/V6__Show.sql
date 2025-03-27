CREATE TABLE shows
(
    show_id    INT PRIMARY KEY AUTO_INCREMENT,
    movie_id   INT,
    screen_id  INT,
    show_date  DATE     NOT NULL,
    show_time  TIME     NOT NULL,
    created_on DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by INT      NOT NULL,
    updated_on DATETIME          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by INT,
    FOREIGN KEY (movie_id) REFERENCES movie (movie_id),
    FOREIGN KEY (screen_id) REFERENCES screen (screen_id),
    FOREIGN KEY (created_by) REFERENCES users (user_id),
    FOREIGN KEY (updated_by) REFERENCES users (user_id)
);

CREATE TABLE show_price_category
(
    show_id          INT NOT NULL,
    seat_category_id INT NOT NULL,
    base_price       INT NOT NULL,
    FOREIGN KEY (show_id) REFERENCES shows (show_id),
    FOREIGN KEY (seat_category_id) REFERENCES seat_category (seat_category_id),
    PRIMARY KEY (show_id, seat_category_id)
);