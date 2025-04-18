use cinego;

CREATE TABLE role
(
    role_id   INT PRIMARY KEY,
    role_name VARCHAR(30) NOT NULL UNIQUE
);

INSERT INTO role
VALUES (1, 'ROLE_SUPER_ADMIN'),
       (2, 'ROLE_THEATRE_ADMIN'),
       (3, 'ROLE_CUSTOMER');

CREATE TABLE state
(
    state_code varchar(2)  NOT NULL PRIMARY KEY,
    state_name varchar(30) NOT NULL
);

INSERT INTO state (state_code, state_name)
VALUES ('MP', 'Madhya Pradesh'),
       ('GJ', 'Gujarat'),
       ('DD', 'Daman and Diu'),
       ('DN', 'Dadra and Nagar Haveli'),
       ('MH', 'Maharashtra');


CREATE TABLE city
(
    city_id    int         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    city_name  varchar(20) NOT NULL,
    state_code varchar(2) DEFAULT NULL,
    FOREIGN KEY (state_code) REFERENCES state (state_code)
);

INSERT INTO city (city_id, city_name, state_code)
VALUES (1, 'Indore', 'MP'),
       (2, 'Bhopal', 'MP'),
       (3, 'Ahmedabad', 'GJ'),
       (4, 'Surat', 'GJ'),
       (5, 'Daman', 'DD'),
       (6, 'Diu', 'DD'),
       (7, 'Silvassa', 'DN'),
       (8, 'Amli', 'DN'),
       (9, 'Mumbai', 'MH'),
       (10, 'Pune', 'MH');

CREATE TABLE address
(
    address_id    INT PRIMARY KEY AUTO_INCREMENT,
    address_line  TEXT NOT NULL,
    address_line2 TEXT,
    pincode       INT  NOT NULL,
    city_id       INT  NOT NULL,
    FOREIGN KEY (city_id) REFERENCES city (city_id)
);

CREATE TABLE users
(
    user_id      INT PRIMARY KEY AUTO_INCREMENT,
    username     VARCHAR(20) NOT NULL UNIQUE,
    password     VARCHAR(50) NOT NULL,
    first_name   VARCHAR(30) NOT NULL,
    last_name    VARCHAR(30) NOT NULL,
    email        VARCHAR(50) NOT NULL UNIQUE,
    phone_number VARCHAR(15) NOT NULL,
    address_id   INT,
    created_on   DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by   INT         NOT NULL,
    updated_on   DATETIME             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by   INT,
    role_id      INT         NOT NULL,
    FOREIGN KEY (address_id) REFERENCES address (address_id),
    FOREIGN KEY (created_by) REFERENCES users (user_id),
    FOREIGN KEY (updated_by) REFERENCES users (user_id),
    FOREIGN KEY (role_id) REFERENCES role (role_id)
);

INSERT INTO address (address_id, address_line, address_line2, pincode, city_id)
VALUES (1, 'Default Address Line 1', 'Default Address Line 2', 395009, 4);

-- Now insert the super admin user
INSERT INTO users (user_id, username, password, first_name, last_name, email, phone_number, address_id, created_by,
                   role_id)
VALUES (1, 'superadmin', 'CineGo@94278', 'Super', 'Admin', 'superadmin@cinego.com', '9427882822', 1, 1, 1);

CREATE TABLE theater
(
    theater_id     INT PRIMARY KEY AUTO_INCREMENT,
    theater_admin  INT         NOT NULL,
    theater_name   VARCHAR(30) NOT NULL,
    theater_rating DECIMAL(3, 1),
    address_id     INT         NOT NULL,
    created_on     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by     INT,
    updated_on     DATETIME             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by     INT,
    FOREIGN KEY (theater_admin) REFERENCES users (user_id),
    FOREIGN KEY (address_id) REFERENCES address (address_id),
    FOREIGN KEY (created_by) REFERENCES users (user_id),
    FOREIGN KEY (updated_by) REFERENCES users (user_id)
);

CREATE TABLE movie
(
    movie_id           INT PRIMARY KEY AUTO_INCREMENT,
    movie_title        VARCHAR(30) NOT NULL,
    movie_rating       DECIMAL(3, 1),
    movie_duration     TIME        NOT NULL,
    movie_release_date DATE        NOT NULL,
    movie_description  TEXT,
    created_on         DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by         INT,
    updated_on         DATETIME             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by         INT,
    FOREIGN KEY (created_by) REFERENCES users (user_id),
    FOREIGN KEY (updated_by) REFERENCES users (user_id)
);

CREATE TABLE languages
(
    language_id   INT PRIMARY KEY,
    language_name VARCHAR(30) NOT NULL UNIQUE
);

INSERT INTO languages (language_id, language_name)
VALUES (1, 'Hindi'),
       (2, 'English'),
       (3, 'Telugu'),
       (4, 'Tamil'),
       (5, 'Punjabi'),
       (6, 'Bengali'),
       (7, 'Marathi'),
       (8, 'Kannada'),
       (9, 'Malayalam'),
       (10, 'Gujarati');

CREATE TABLE genres
(
    genre_id   INT PRIMARY KEY,
    genre_name VARCHAR(30) NOT NULL UNIQUE
);

INSERT INTO genres (genre_id, genre_name)
VALUES (1, 'Action'),
       (2, 'Comedy'),
       (3, 'Drama'),
       (4, 'Romance'),
       (5, 'Thriller'),
       (6, 'Horror'),
       (7, 'Sci-Fi'),
       (8, 'Fantasy'),
       (9, 'Adventure'),
       (10, 'Animation');

CREATE TABLE formats
(
    format_id   int         NOT NULL,
    format_name varchar(20) NOT NULL,
    PRIMARY KEY (format_id)
);

INSERT INTO formats (format_id, format_name)
VALUES (1, '2D'),
       (2, '3D'),
       (3, 'IMAX'),
       (4, 'Dolby Cinema'),
       (5, '4K'),
       (6, '4DX');

CREATE TABLE crew
(
    crew_id   INT PRIMARY KEY AUTO_INCREMENT,
    crew_name VARCHAR(30) NOT NULL UNIQUE
);

INSERT INTO crew (crew_name)
VALUES ('Shah Rukh Khan'),
       ('Amitabh Bachchan'),
       ('Aamir Khan'),
       ('Salman Khan'),
       ('Rajinikanth'),
       ('Akshay Kumar'),
       ('Hrithik Roshan'),
       ('Ranbir Kapoor'),
       ('Ranveer Singh'),
       ('Ajay Devgn'),
       ('Mahesh Babu'),
       ('Allu Arjun'),
       ('Vijay Thalapathy'),
       ('Shahid Kapoor'),
       ('Tiger Shroff'),
       ('Prabhas'),
       ('Kamal Haasan'),
       ('NTR Jr.'),
       ('Ram Charan'),
       ('Yash');

CREATE TABLE crew_designation
(
    designation_id    INT PRIMARY KEY AUTO_INCREMENT,
    designation_title VARCHAR(30) NOT NULL UNIQUE
);

INSERT INTO crew_designation (designation_title)
VALUES ('Director'),
       ('Assistant Director'),
       ('Director of Photography'),
       ('Camera Operator'),
       ('Gaffer'),
       ('Key Grip'),
       ('Production Designer'),
       ('Art Director'),
       ('Costume Designer'),
       ('Makeup Artist'),
       ('Hair Stylist'),
       ('Production Manager'),
       ('Location Manager');

CREATE TABLE movie_languages
(
    movie_id    INT NOT NULL,
    language_id INT NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movie (movie_id),
    FOREIGN KEY (language_id) REFERENCES languages (language_id),
    PRIMARY KEY (movie_id, language_id)
);

CREATE TABLE movie_genres
(
    movie_id INT NOT NULL,
    genre_id INT NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movie (movie_id),
    FOREIGN KEY (genre_id) REFERENCES genres (genre_id),
    PRIMARY KEY (movie_id, genre_id)
);

CREATE TABLE movie_formats
(
    movie_id  INT NOT NULL,
    format_id INT NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movie (movie_id),
    FOREIGN KEY (format_id) REFERENCES formats (format_id),
    PRIMARY KEY (movie_id, format_id)
);

CREATE TABLE movie_crew
(
    movie_id       INT NOT NULL,
    crew_id        INT NOT NULL,
    designation_id INT NOT NULL,
    character_name VARCHAR(30),
    FOREIGN KEY (movie_id) REFERENCES movie (movie_id),
    FOREIGN KEY (crew_id) REFERENCES crew (crew_id),
    FOREIGN KEY (designation_id) REFERENCES crew_designation (designation_id),
    PRIMARY KEY (movie_id, crew_id, designation_id)
);

CREATE TABLE screen_types
(
    screen_type_id          INT PRIMARY KEY AUTO_INCREMENT,
    screen_type             VARCHAR(10) NOT NULL UNIQUE,
    screen_type_description TEXT
);

INSERT INTO screen_types (screen_type, screen_type_description)
VALUES ('2D', 'Standard 2D screen'),
       ('3D', '3D screen with polarized glasses'),
       ('IMAX', 'Large format screen with enhanced visuals'),
       ('4DX', 'Motion and sensory effects screen'),
       ('GOLD', 'Premium screen with luxury seating and service'),
       ('SCREENX', 'Panoramic 270-degree screen'),
       ('MX4D', 'Motion and environmental effects screen'),
       ('ONYX', 'LED cinema screen with HDR'),
       ('PLAYHOUSE', 'Kids-focused screen with play area'),
       ('INSIGNIA', 'Premium screen with recliner seats and service');

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
    show_id          INT            NOT NULL,
    seat_category_id INT            NOT NULL,
    base_price       DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (show_id) REFERENCES shows (show_id),
    FOREIGN KEY (seat_category_id) REFERENCES seat_category (seat_category_id),
    PRIMARY KEY (show_id, seat_category_id)
);

CREATE TABLE show_seats
(
    show_id    INT            NOT NULL,
    seat_id    INT            NOT NULL,
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

ALTER TABLE show_seats
    ADD COLUMN hold_until TIMESTAMP NULL AFTER available;