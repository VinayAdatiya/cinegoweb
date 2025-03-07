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
    }

    public static class Error {
        public static final String DB_CONNECTION_FAILED = "Database connection Failed!";
        public static final String REQUIRED_FIELD_MISSING = "Please Fill in all required fields!";
        public static final String EMAIL_EXISTS = "Email already exists!";
        public static final String USERNAME_EXISTS = "Username already taken!";
        public static final String INVALID_CREDENTIALS = "Invalid email or password!";
        public static final String USER_NOT_FOUND = "User not found!";
        public static final String SESSION_EXPIRED = "Session expired! Please login again.";
        public static final String INTERNAL_ERROR = "Something went wrong. Please try again later.";
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
        public static final String THEATER_FAILED = "Failed to list Theater!";
        public static final String THEATER_ADMIN_FAILED = "Failed to register Theater Admin";
        public static final String NO_RECORD_FOUND = "No Record Found";
    }
}