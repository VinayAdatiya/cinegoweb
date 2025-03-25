CREATE TABLE screen
(
    screen_id      INT PRIMARY KEY AUTO_INCREMENT,
    screen_title   VARCHAR(10) NOT NULL,
    total_seats    INT         NOT NULL,
    screen_type_id INT,
    layout         TEXT        NOT NULL,
    theater_id     INT,
    created_on     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by     INT         NOT NULL,
    updated_on     DATETIME             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by     INT,
    FOREIGN KEY (screen_type_id) REFERENCES screen_types (screen_type_id),
    FOREIGN KEY (theater_id) REFERENCES theater (theater_id),
    FOREIGN KEY (created_by) REFERENCES users (user_id),
    FOREIGN KEY (updated_by) REFERENCES users (user_id)
);