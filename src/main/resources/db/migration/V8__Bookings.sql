CREATE TABLE payment_methods
(
    payment_method_id INT PRIMARY KEY AUTO_INCREMENT,
    payment_method    VARCHAR(255) NOT NULL UNIQUE
);

INSERT INTO payment_methods (payment_method)
VALUES ('Credit Card'),
       ('Debit Card'),
       ('Net Banking'),
       ('UPI'),
       ('Wallet (Paytm)'),
       ('Wallet (PhonePe)'),
       ('Wallet (Amazon Pay)');

CREATE TABLE bookings
(
    booking_id        INT PRIMARY KEY AUTO_INCREMENT,
    show_id           INT       NOT NULL,
    user_id           INT       NOT NULL,
    grand_total       DECIMAL(11, 2),
    number_of_seats   INT       NOT NULL,
    booking_status    ENUM ('Pending', 'Confirmed', 'Canceled', 'Expired', 'Failed', 'Refunded'),
    payment_method_id INT       NOT NULL,
    created_on        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by        INT       NOT NULL,
    updated_on        TIMESTAMP          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by        INT,
    FOREIGN KEY (show_id) REFERENCES shows (show_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (payment_method_id) REFERENCES payment_methods (payment_method_id),
    FOREIGN KEY (created_by) REFERENCES users (user_id),
    FOREIGN KEY (updated_by) REFERENCES users (user_id)
);

CREATE TABLE booked_seats
(
    booking_id INT NOT NULL,
    seat_id    INT NOT NULL,
    PRIMARY KEY (booking_id, seat_id),
    FOREIGN KEY (booking_id) REFERENCES bookings (booking_id),
    FOREIGN KEY (seat_id) REFERENCES seats (seat_id)
);