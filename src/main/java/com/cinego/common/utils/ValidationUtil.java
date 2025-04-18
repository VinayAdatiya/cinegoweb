package com.cinego.common.utils;

import java.util.regex.Pattern;

public class ValidationUtil {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,50}$";
    private static String ONLY_NUMBER_REGEX = "[0-9]+";
    private static String TIME_REGEX = "^([0-1][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";
    private static String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";

    public static boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    public static boolean isValidPassword(String password) {
        return Pattern.matches(PASSWORD_REGEX, password);
    }

    public static boolean isValidLength(String value, int maxLength) {
        return value != null && value.length() <= maxLength;
    }

    public static boolean isNumeric(String value) {
        return value.matches(ONLY_NUMBER_REGEX);
    }

    public static boolean isValidTime(String durationStr) {
        return durationStr.trim().matches(TIME_REGEX);
    }

    public static boolean isValidDate(String date) {
        return date.matches(DATE_REGEX);
    }
}