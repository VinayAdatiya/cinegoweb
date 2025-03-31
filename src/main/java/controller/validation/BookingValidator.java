package controller.validation;

import common.Message;
import common.exception.ApplicationException;
import dto.booking.BookingRequestDTO;
import common.utils.DatabaseUtil;

public class BookingValidator {

    public static void validateCreateBooking(BookingRequestDTO bookingRequestDTO) throws ApplicationException {
        if (bookingRequestDTO == null) {
            throw new ApplicationException(Message.Error.INVALID_INPUT);
        }

        if (bookingRequestDTO.getShowId() <= 0) {
            throw new ApplicationException(Message.Error.INVALID_ID); // Define this message
        }
        if (!DatabaseUtil.checkRecordExists("shows", "show_id", bookingRequestDTO.getShowId())) {
            throw new ApplicationException(Message.Error.INVALID_ID);
        }

        if (bookingRequestDTO.getUserId() <= 0) {
            throw new ApplicationException(Message.Error.USER_NOT_FOUND); // Define this message
        }
        if (!DatabaseUtil.checkRecordExists("users", "user_id", bookingRequestDTO.getUserId())) {
            throw new ApplicationException(Message.Error.INVALID_ID);
        }

        if (bookingRequestDTO.getNumberOfSeats() <= 0) {
            throw new ApplicationException(Message.Error.INVALID_INPUT);
        }

        if (bookingRequestDTO.getPaymentMethodId() <= 0) {
            throw new ApplicationException(Message.Error.INVALID_ID);
        }
        if (!DatabaseUtil.checkRecordExists("payment_methods", "payment_method_id", bookingRequestDTO.getPaymentMethodId())) {
            throw new ApplicationException(Message.Error.INVALID_ID);
        }

        if (bookingRequestDTO.getShowSeatList() == null || bookingRequestDTO.getShowSeatList().isEmpty()) {
            throw new ApplicationException(Message.Error.INVALID_INPUT);
        }
    }

    public static void validateConfirmBooking(BookingRequestDTO bookingRequestDTO) throws ApplicationException {
        if (bookingRequestDTO == null) {
            throw new ApplicationException(Message.Error.INVALID_INPUT);
        }

        if (bookingRequestDTO.getBookingId() <= 0) {
            throw new ApplicationException(Message.Error.INVALID_ID);
        }
        if (!DatabaseUtil.checkRecordExists("bookings", "booking_id", bookingRequestDTO.getBookingId())) {
            throw new ApplicationException(Message.Error.INVALID_ID);
        }
    }

    public static void validateResetBooking(int bookingId) {
        if(!DatabaseUtil.checkRecordExists("booking","booking_id",bookingId)){
            throw new ApplicationException(Message.Error.NO_RECORD_FOUND);
        }
    }
}