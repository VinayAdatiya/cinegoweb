package common;

public class Message {

    public static class Success {
        public static final String DB_CONNECTION_SUCCESS = "Database Connected Successful!";
        public static final String FLYWAY_SUCCESS = "Flyway migration applied successfully.";
        public static final String LOGIN_SUCCESS = "Login Successful!";
        public static final String SIGNUP_SUCCESS = "Signup Completed Successfully!";
        public static final String LOGOUT_SUCCESS = "Logout Successful!";
        public static final String PROFILE_UPDATED = "Profile Updated Successfully!";
        public static final String THEATER_SUCCESS = "Theater added Successfully!";
        public static final String THEATER_ADMIN_REGISTERED = "Theater Admin registered successfully";
        public static final String RECORD_FOUND = "Record retrieved Successfully";
        public static final String USER_FOUND = "User fetched successfully";
        public static final String MOVIE_ADDED = "Movie added successfully.";
        public static final String CREW_ADDED = "Crew member added successfully.";
        public static final String CREW_FOUND = "crew members Fetched successfully.";
        public static final String MOVIES_FOUND = "Movies fetched successfully";
        public static final String THEATERS_FOUND = "Theaters fetched successfully";
        public static final String CITIES_FOUND = "Cities fetched successfully.";
        public static final String LANGUAGES_FOUND = "Languages fetched successfully.";
        public static final String GENRES_FOUND = "Genres fetched successfully.";
        public static final String FORMATS_FOUND = "Formats fetched successfully.";
        public static final String DESIGNATIONS_FOUND = "Designations are fetched successfully";
        public static final String RECORD_UPDATED = "Record updated successfully";
        public static final String RECORD_DELETED = "Record deleted successfully";
        public static final String SCREEN_ADDED = "Screen added successfully";
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
        public static final String SCREEN_LAYOUT_TOO_LONG = "Screen layout is too long.";
    }
}