package com.cinego.controller.booking;

import com.cinego.controller.validation.BookingValidator;
import com.cinego.dto.user.UserResponseDTO;
import com.cinego.service.BookingService;
import com.cinego.common.AppConstant;
import com.cinego.common.Message;
import com.cinego.common.exception.ApplicationException;
import com.cinego.common.exception.DBException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static com.cinego.common.utils.ResponseUtils.createResponse;

@WebServlet(name = "ResetBookingController", value = "/resetBooking", description = "Reset On Hold Booking")
public class ResetBookingController extends HttpServlet {
    private final BookingService bookingService = new BookingService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        HttpSession session = request.getSession(false);
        try {
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
            UserResponseDTO currentUser = (UserResponseDTO) session.getAttribute("user");
            int currentUserId = currentUser.getUserId();
            BookingValidator.validateResetBooking(bookingId);
            bookingService.resetBooking(bookingId, currentUserId);
            createResponse(response, Message.Success.BOOKING_EXPIRED, null, HttpServletResponse.SC_OK);
        } catch (DBException e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NumberFormatException e) {
            throw new ApplicationException(Message.Error.INVALID_ID);
        } catch (ApplicationException e) {
            createResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

