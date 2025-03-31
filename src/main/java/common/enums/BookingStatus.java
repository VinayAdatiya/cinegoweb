package common.enums;

import java.util.Arrays;

public enum BookingStatus {

    PENDING("Pending"),
    CONFIRMED("Confirmed"),
    CANCELED("Canceled"),
    EXPIRED("Expired"),
    FAILED("Failed"),
    REFUNDED("Refunded");

    private final String status;

    BookingStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static BookingStatus getBookingStatus(String status) {
        return Arrays.stream(BookingStatus.values())
                .filter(bookingStatus -> bookingStatus.getStatus().equalsIgnoreCase(status))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid booking status: " + status));
    }
}