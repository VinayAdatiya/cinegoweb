package com.cinego.common;

public class Message {

    public static class Success {
        public static final String DB_CONNECTION_SUCCESS = "Database Connected Successful!";
        public static final String FLYWAY_SUCCESS = "Flyway migration applied successfully.";
        public static final String LOGIN_SUCCESS = "Login Successful!";
        public static final String SIGNUP_SUCCESS = "Signup Completed Successfully!";
        public static final String LOGOUT_SUCCESS = "Logout Successful!";
        public static final String THEATER_SUCCESS = "Theater added Successfully!";
        public static final String THEATER_ADMIN_REGISTERED = "Theater Admin registered successfully";
        public static final String RECORD_FOUND = "Record retrieved Successfully";
        public static final String USER_FOUND = "User fetched successfully";
        public static final String MOVIE_ADDED = "Movie added successfully.";
        public static final String CREW_ADDED = "Crew member added successfully.";
        public static final String CREW_FOUND = "crew members Fetched successfully.";
        public static final String MOVIES_FOUND = "Movies fetched successfully";
        public static final String SHOWS_FOUND = "Shows fetched successfully";
        public static final String THEATERS_FOUND = "Theaters fetched successfully";
        public static final String CITIES_FOUND = "Cities fetched successfully.";
        public static final String LANGUAGES_FOUND = "Languages fetched successfully.";
        public static final String GENRES_FOUND = "Genres fetched successfully.";
        public static final String FORMATS_FOUND = "Formats fetched successfully.";
        public static final String DESIGNATIONS_FOUND = "Designations are fetched successfully";
        public static final String RECORD_UPDATED = "Record updated successfully";
        public static final String RECORD_DELETED = "Record deleted successfully";
        public static final String SCREEN_ADDED = "Screen added successfully";
        public static final String SHOW_ADDED = "Show added successfully";
        public static final String BOOKING_SUCCESS = "Booking Created Successfully";
        public static final String BOOKING_CONFIRMED = "Booking Confirmed ! Enjoy Your Movie";
        public static final String BOOKING_EXPIRED = "Booking timing expired";
        public static final String BOOKING_CANCELLED = "Booking cancelled successfully !!!";
        public static final String PAYMENT_METHODS_FOUND = "Payments Methods fetched successfully";
    }

    public static class Error {
        public static final String DB_CONNECTION_FAILED = "Database connection Failed!";
        public static final String FLYWAY_FAILED = "Flyway migration failed!";
        public static final String REQUIRED_FIELD_MISSING = "Please Fill in all required fields!";
        public static final String EMAIL_EXISTS = "Email already exists!";
        public static final String USERNAME_EXISTS = "Username already taken!";
        public static final String INVALID_CREDENTIALS = "Invalid email or password!";
        public static final String USER_NOT_FOUND = "User not found!";
        public static final String LOGIN_FIRST = "User not found Please login first!";
        public static final String SESSION_EXPIRED = "Session expired! Please login again.";
        public static final String INTERNAL_ERROR = "Something went wrong on our end. Please try again later.";
        public static final String EMAIL_REQUIRED = "Email is required!";
        public static final String EMAIL_INVALID = "Invalid email format!";
        public static final String EMAIL_TOO_LONG = "Email length cannot exceed 50 characters!";
        public static final String PASSWORD_TOO_SHORT = "Password must be at least 8 characters long!";
        public static final String PASSWORD_TOO_LONG = "Password cannot exceed 50 characters!";
        public static final String PASSWORD_WEAK = "Password must contain at least 1 uppercase letter, 1 lowercase letter, 1 digit, and 1 special character!";
        public static final String USERNAME_TOO_LONG = "Username cannot exceed 20 characters!";
        public static final String FIRST_NAME_TOO_LONG = "First name cannot exceed 30 characters!";
        public static final String LAST_NAME_TOO_LONG = "Last name cannot exceed 30 characters!";
        public static final String PHONE_INVALID = "Phone number must be numeric and up to 15 digits!";
        public static final String PHONE_TOO_LONG = "Phone number is too long";
        public static final String ADDRESS_TOO_LONG = "Address is too long";
        public static final String PIN_CODE_INVALID = "Pin code must be numeric!";
        public static final String PINCODE_INVALID = "Please enter a valid pincode";
        public static final String THEATER_NAME_TOO_LONG = "Theater name is too long! It cannot exceed 30 characters!";
        public static final String THEATER_FAILED = "Failed to list Theater!";
        public static final String THEATER_ADMIN_NOT_FOUND = "Theater Admin not found";
        public static final String THEATER_ADMIN_FAILED = "Failed to register Theater Admin";
        public static final String NO_RECORD_FOUND = "No Record Found";
        public static final String INVALID_INPUT = "Please Enter Valid Input";
        public static final String MOVIE_TITLE_REQUIRED = "Movie title is required.";
        public static final String MOVIE_TITLE_TOO_LONG = "Movie title cannot exceed 30 characters!";
        public static final String MOVIE_RATING_INVALID = "Movie rating is invalid. It must be between 0 and 10.";
        public static final String MOVIE_DURATION_REQUIRED = "Movie duration is required.";
        public static final String MOVIE_DURATION_INVALID = "Movie duration is invalid. It must be in HH:MM:SS format.";
        public static final String MOVIE_RELEASE_DATE_REQUIRED = "Movie release date is required.";
        public static final String CREW_NAME_REQUIRED = "Crew Name is Required";
        public static final String MOVIE_CREW_ENTRIES_NULL = "Movie crew entries are null.";
        public static final String CHARACTER_NAME_TOO_LONG = "Character name is too long.";
        public static final String MOVIE_LANGUAGES_LIST_NULL = "Movie languages list is null.";
        public static final String MOVIE_GENRES_LIST_NULL = "Movie genres list is null.";
        public static final String MOVIE_FORMATS_LIST_NULL = "Movie formats list is null.";
        public static final String POSTER_PATH_REQUIRED = "Enter valid poster path.";
        public static final String STORE_FAILED = "Failed to store poster";
        public static final String INVALID_ID = "Invalid Id Entry";
        public static final String SCREEN_TITLE_REQUIRED = "Screen title is required.";
        public static final String SCREEN_TITLE_TOO_LONG = "Screen title is too long (max 10 characters).";
        public static final String SCREEN_TOTAL_SEATS_INVALID = "Total seats must be greater than zero.";
        public static final String INVALID_SCREEN_TYPE_ID = "Invalid screen type ID.";
        public static final String SCREEN_LAYOUT_REQUIRED = "Screen layout is required.";
        public static final String SCREEN_THEATER_ID_REQUIRED = "Theater ID is required.";
        public static final String INVALID_THEATER_ID = "Invalid theater ID.";
        public static final String INVALID_ADMIN_PRIVILEGED = "You dont have valid privileged to access this theater.";
        public static final String INVALID_SHOW_PRICE_CATEGORY = "Invalid show price category list";
        public static final String BASE_PRICE_INVALID = "Base price must be greater than 0";
        public static final String NOT_VALID_DATE = "Date is not valid";
        public static final String INVALID_JSON_REQUEST = "Invalid JSON Request !!! ";
        public static final String SERVER_ERROR = "Server ";
        public static final String PAYMENT_METHOD_NOT_FOUND = "Invalid payment method";
        public static final String SEAT_NOT_AVAILABLE = "The selected seats are not available. Please choose other seats.";
        public static final String INVALID_SEAT_SELECTION = "You need to select minimum 1 seat for proceeding further !!! ";
        public static final String SEAT_SELECTION_TOO_LARGE = "For assistance with booking large groups, please contact CineGo services.";
        public static final String SHOW_ALREADY_ENDED = "This show has already ended and is not available for booking.";
        public static final String INVALID_BOOKING_STATUS = "Booking cannot be confirmed. Invalid booking status.";
        public static final String UNAUTHORIZED_ACCESS = "You are not authorized to confirm this booking.";
        public static final String SHOW_SCHEDULED_ON_SCREEN = "Cannot delete screen. Shows are currently scheduled for this screen.";
    }

    public static final class CORSConstants {
        public static final String HEADER_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
        public static final String HEADER_ALLOW_METHODS = "Access-Control-Allow-Methods";
        public static final String HEADER_ALLOW_HEADERS = "Access-Control-Allow-Headers";
        public static final String HEADER_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";

        public static final String VALUE_ALLOW_ALL = "Origin";
        public static final String VALUE_ALLOW_METHODS = "GET, POST, PUT, DELETE, OPTIONS";
        public static final String VALUE_ALLOW_HEADERS = "Content-Type, Authorization";
        public static final String VALUE_ALLOW_CREDENTIALS = "true";
    }
}