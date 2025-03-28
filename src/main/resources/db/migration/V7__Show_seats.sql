CREATE TABLE show_seats
(
    show_id    INT            NOT NULL,
    seat_id    INT            NOT NULL AUTO_INCREMENT,
    seat_price DECIMAL(10, 2) NOT NULL,
    is_booked  BIT            NOT NULL DEFAULT 0,
    available  BIT            NOT NULL DEFAULT 1,
    created_on TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by INT,
    updated_on TIMESTAMP               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by INT,
    FOREIGN KEY (show_id) REFERENCES shows (show_id),
    FOREIGN KEY (seat_id) REFERENCES seats (seat_id),
    FOREIGN KEY (created_by) REFERENCES users (user_id),
    FOREIGN KEY (updated_by) REFERENCES users (user_id),
    PRIMARY KEY (show_id, seat_id)
);
