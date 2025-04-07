package com.cinego.controller.validation;

import com.cinego.common.Message;
import com.cinego.common.exception.ApplicationException;
import com.cinego.dto.booking.BookingRequestDTO;
import com.cinego.common.utils.DatabaseUtil;

public class BookingValidator {

    public static void validateCreateBooking(BookingRequestDTO bookingRequestDTO) throws ApplicationException {
        if (bookingRequestDTO == null) {
            throw new ApplicationException(Message.Error.INVALID_INPUT);
        }

        if (bookingRequestDTO.getShowId() <= 0) {
            throw new ApplicationException(Message.Error.INVALID_ID);
        }

        if (!DatabaseUtil.checkRecordExists("shows", "show_id", bookingRequestDTO.getShowId())) {
            throw new ApplicationException(Message.Error.INVALID_ID);
        }

        if (bookingRequestDTO.getUserId() <= 0) {
            throw new ApplicationException(Message.Error.USER_NOT_FOUND);
        }
        if (!DatabaseUtil.checkRecordExists("users", "user_id", bookingRequestDTO.getUserId())) {
            throw new ApplicationException(Message.Error.USER_NOT_FOUND);
        }

        if (bookingRequestDTO.getNumberOfSeats() <= 0) {
            throw new ApplicationException(Message.Error.INVALID_SEAT_SELECTION);
        }

        if (bookingRequestDTO.getNumberOfSeats() >= 11) {
            throw new ApplicationException(Message.Error.SEAT_SELECTION_TOO_LARGE);
        }

        if (bookingRequestDTO.getPaymentMethodId() <= 0) {
            throw new ApplicationException(Message.Error.INVALID_ID);
        }

        if (!DatabaseUtil.checkRecordExists("payment_methods", "payment_method_id", bookingRequestDTO.getPaymentMethodId())) {
            throw new ApplicationException(Message.Error.INVALID_ID);
        }

        if (bookingRequestDTO.getShowSeatList() == null || bookingRequestDTO.getShowSeatList().isEmpty()) {
            throw new ApplicationException(Message.Error.INVALID_SEAT_SELECTION);
        }

        if (bookingRequestDTO.getShowSeatList().size() != bookingRequestDTO.getNumberOfSeats()) {
            throw new ApplicationException(Message.Error.INVALID_INPUT);
        }
    }

    public static void validateConfirmBooking(int bookingId) throws ApplicationException {
        if (bookingId <= 0) {
            throw new ApplicationException(Message.Error.INVALID_ID);
        }

        if (!DatabaseUtil.checkRecordExists("bookings", "booking_id", bookingId)) {
            throw new ApplicationException(Message.Error.INVALID_ID);
        }
    }

    public static void validateResetBooking(int bookingId) {
        if (bookingId <= 0) {
            throw new ApplicationException(Message.Error.INVALID_ID);
        }

        if (!DatabaseUtil.checkRecordExists("bookings", "booking_id", bookingId)) {
            throw new ApplicationException(Message.Error.NO_RECORD_FOUND);
        }
    }
}