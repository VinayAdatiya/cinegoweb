package common;

public class Message {

    public static class Success {
        public static final String DB_CONNECTION_SUCCESS = "Database Connected Successful!";
        public static final String LOGIN_SUCCESS = "Login Successful!";
        public static final String SIGNUP_SUCCESS = "Signup Completed Successfully!";
        public static final String LOGOUT_SUCCESS = "Logout Successful!";
        public static final String PROFILE_UPDATED = "Profile Updated Successfully!";
        public static final String THEATER_SUCCESS = "Theater added Successfully!";
        public static final String THEATER_ADMIN_REGISTERED = "Theater Admin registered successfully";
        public static final String USER_FOUND = "User fetched successfully";
        public static final String MOVIE_ADDED = "Movie added successfully.";
        public static final String CREW_ADDED = "Crew member added successfully.";
        public static final String MOVIE_CREW_ADDED = "Movie crew members added successfully.";
        public static final String MOVIE_LANGUAGES_ADDED = "Movie languages added successfully.";
        public static final String MOVIE_GENRES_ADDED = "Movie genres added successfully.";
        public static final String MOVIE_FORMATS_ADDED = "Movie formats added successfully.";
    }

    public static class Error {
        public static final String DB_CONNECTION_FAILED = "Database connection Failed!";
        public static final String REQUIRED_FIELD_MISSING = "Please Fill in all required fields!";
        public static final String EMAIL_EXISTS = "Email already exists!";
        public static final String USERNAME_EXISTS = "Username already taken!";
        public static final String INVALID_CREDENTIALS = "Invalid email or password!";
        public static final String USER_NOT_FOUND = "User not found!";
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
        public static final String MOVIE_TITLE_REQUIRED = "Movie title is required.";
        public static final String MOVIE_RATING_INVALID = "Movie rating is invalid. It must be between 0 and 10.";
        public static final String MOVIE_DURATION_REQUIRED = "Movie duration is required.";
        public static final String MOVIE_DURATION_INVALID = "Movie duration is invalid. It must be in HH:MM:SS format.";
        public static final String MOVIE_RELEASE_DATE_REQUIRED = "Movie release date is required.";
        public static final String MOVIE_RELEASE_DATE_FUTURE = "Movie release date cannot be in the future.";
        public static final String CREW_NAME_REQUIRED = "Crew Name is Required";
        public static final String MOVIE_CREW_ENTRIES_INVALID = "Movie crew entries are invalid.";
        public static final String MOVIE_CREW_ENTRY_INVALID = "Movie crew entry is invalid.";
        public static final String MOVIE_LANGUAGES_LIST_NULL = "Movie languages list is null.";
        public static final String MOVIE_ID_INVALID = "Movie ID is invalid.";
        public static final String MOVIE_LANGUAGES_EMPTY = "Movie languages list is empty.";
        public static final String LANGUAGE_ID_INVALID = "Language ID is invalid.";
        public static final String MOVIE_GENRES_LIST_NULL = "Movie genres list is null.";
        public static final String MOVIE_GENRES_EMPTY = "Movie genres list is empty.";
        public static final String GENRE_ID_INVALID = "Genre ID is invalid.";
        public static final String MOVIE_FORMATS_LIST_NULL = "Movie formats list is null.";
        public static final String MOVIE_FORMATS_EMPTY = "Movie formats list is empty.";
        public static final String FORMAT_ID_INVALID = "Format ID is invalid.";
    }
}