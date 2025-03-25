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