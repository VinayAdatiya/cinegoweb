use cinego;

CREATE TABLE role(
	role_id INT PRIMARY KEY,
    role_name VARCHAR(30) NOT NULL UNIQUE
);

INSERT INTO role 
VALUES (1,'ROLE_SUPER_ADMIN'),
	(2,'ROLE_THEATRE_ADMIN'),
	(3,'ROLE_CUSTOMER');

CREATE TABLE state (
  state_code varchar(2) NOT NULL PRIMARY KEY,
  state_name varchar(30) NOT NULL
);

INSERT INTO state (state_code, state_name) VALUES
	('MP', 'Madhya Pradesh'),
	('GJ', 'Gujarat'),
	('DD', 'Daman and Diu'),
	('DN', 'Dadra and Nagar Haveli'),
	('MH', 'Maharashtra');


CREATE TABLE city (
  city_id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  city_name varchar(20) NOT NULL,
  state_code varchar(2) DEFAULT NULL,
  FOREIGN KEY (state_code) REFERENCES state(state_code)
  );
  
INSERT INTO city (city_id, city_name, state_code) VALUES
	(1, 'Indore', 'MP'),
	(2, 'Bhopal', 'MP'),
	(3, 'Ahmedabad', 'GJ'),
	(4, 'Surat', 'GJ'),
	(5, 'Daman', 'DD'),
	(6, 'Diu', 'DD'),
	(7, 'Silvassa', 'DN'),
	(8, 'Amli', 'DN'),
	(9, 'Mumbai', 'MH'),
	(10, 'Pune', 'MH');

CREATE TABLE address (
    address_id INT PRIMARY KEY AUTO_INCREMENT,
    address_line TEXT NOT NULL,
    address_line2 TEXT,
    pincode INT NOT NULL,
    city_id INT NOT NULL,
    FOREIGN KEY (city_id) REFERENCES city(city_id)
);

CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    phone_number VARCHAR(15) NOT NULL,
    address_id INT, 
    created_on DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by INT NOT NULL,
    updated_on DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by INT,
    role_id INT NOT NULL,
    FOREIGN KEY (address_id) REFERENCES address(address_id), 
    FOREIGN KEY (created_by) REFERENCES users(user_id), 
    FOREIGN KEY (updated_by) REFERENCES users(user_id),
    FOREIGN KEY (role_id) REFERENCES role(role_id)
);

INSERT INTO address (address_id, address_line, address_line2, pincode, city_id) 
VALUES (1, 'Default Address Line 1', 'Default Address Line 2', 395009, 4);

-- Now insert the super admin user
INSERT INTO users (user_id, username, password, first_name, last_name, email, phone_number, address_id, created_by, role_id)
VALUES (1, 'superadmin', 'CineGo@94278', 'Super', 'Admin', 'superadmin@cinego.com', '9427882822', 1, 1, 1);

CREATE TABLE theater (
    theater_id INT PRIMARY KEY AUTO_INCREMENT,
    theater_admin INT NOT NULL, 
    theater_name VARCHAR(30) NOT NULL,
    theater_rating DECIMAL(3,1),
    address_id INT NOT NULL, 
    created_on DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by INT, 
    updated_on DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by INT, 
    FOREIGN KEY (theater_admin) REFERENCES users(user_id),
    FOREIGN KEY (address_id) REFERENCES address(address_id),
    FOREIGN KEY (created_by) REFERENCES users(user_id),
    FOREIGN KEY (updated_by) REFERENCES users(user_id) 
);

CREATE TABLE movie (
    movie_id INT PRIMARY KEY AUTO_INCREMENT,
    movie_title VARCHAR(30) NOT NULL,
    movie_rating DECIMAL(3,1),
    movie_duration TIME NOT NULL,
    movie_release_date DATE NOT NULL,
    movie_description TEXT,
    created_on DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by INT, 
    updated_on DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by INT, 
    FOREIGN KEY (created_by) REFERENCES users(user_id),  
    FOREIGN KEY (updated_by) REFERENCES users(user_id)
);

CREATE TABLE languages(
	language_id INT PRIMARY KEY,
    language_name VARCHAR(30) NOT NULL UNIQUE
);

INSERT INTO languages (language_id, language_name) VALUES
    (1, 'Hindi'),
    (2, 'English'),
    (3, 'Telugu'),
    (4, 'Tamil'),
    (5, 'Punjabi'),
    (6, 'Bengali'),
    (7, 'Marathi'),
    (8, 'Kannada'),
    (9, 'Malayalam'),
    (10, 'Gujarati');

CREATE TABLE genres(
	genre_id INT PRIMARY KEY,
    genre_name VARCHAR(30) NOT NULL UNIQUE
);

INSERT INTO genres (genre_id, genre_name) VALUES
    (1, 'Action'),
    (2, 'Comedy'),
    (3, 'Drama'),
    (4, 'Romance'),
    (5, 'Thriller'),
    (6, 'Horror'),
    (7, 'Sci-Fi'),
    (8, 'Fantasy'),
    (9, 'Adventure'),
    (10, 'Animation');
    
CREATE TABLE formats (
  format_id int NOT NULL,
  format_name varchar(20) NOT NULL,
  PRIMARY KEY (format_id)
);

INSERT INTO formats (format_id, format_name) VALUES
	(1, '2D'),
	(2, '3D'),
	(3, 'IMAX'),
	(4, 'Dolby Cinema'),
	(5, '4K'),
	(6, '4DX');

CREATE TABLE crew (
    crew_id INT PRIMARY KEY AUTO_INCREMENT,
    crew_name VARCHAR(30) NOT NULL UNIQUE
);

INSERT INTO crew (crew_name) VALUES 
    ('Shah Rukh Khan'),
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

CREATE TABLE crew_designation (
    designation_id INT PRIMARY KEY AUTO_INCREMENT,
    designation_title VARCHAR(30) NOT NULL UNIQUE
);

INSERT INTO crew_designation (designation_title) VALUES 
    ('Director'),
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

CREATE TABLE movie_languages (
    movie_id INT NOT NULL,
    language_id INT NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movie(movie_id),
    FOREIGN KEY (language_id) REFERENCES languages(language_id),
    PRIMARY KEY (movie_id, language_id)
);

CREATE TABLE movie_genres (
    movie_id INT NOT NULL,
    genre_id INT NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movie(movie_id),
    FOREIGN KEY (genre_id) REFERENCES genres(genre_id),
    PRIMARY KEY (movie_id, genre_id)
);

CREATE TABLE movie_formats (
    movie_id INT NOT NULL,
    format_id INT NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movie(movie_id),
    FOREIGN KEY (format_id) REFERENCES formats(format_id),
    PRIMARY KEY (movie_id, format_id)
);

CREATE TABLE movie_crew (
    movie_id INT NOT NULL,
    crew_id INT NOT NULL,
    designation_id INT NOT NULL,
    character_name VARCHAR(30),
    FOREIGN KEY (movie_id) REFERENCES movie(movie_id),
    FOREIGN KEY (crew_id) REFERENCES crew(crew_id),
    FOREIGN KEY (designation_id) REFERENCES crew_designation(designation_id),
    PRIMARY KEY (movie_id, crew_id, designation_id)
);