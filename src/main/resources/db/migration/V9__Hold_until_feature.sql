ALTER TABLE show_seats
    ADD COLUMN hold_until TIMESTAMP NULL AFTER available;