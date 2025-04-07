package com.cinego.controller.booking;

import com.cinego.dto.user.UserResponseDTO;
import com.cinego.service.BookingService;
import com.cinego.common.AppConstant;
import com.cinego.common.Message;
import com.cinego.common.exception.ApplicationException;
import com.cinego.common.exception.DBException;
import com.cinego.common.utils.ObjectMapperUtil;
import com.cinego.controller.validation.BookingValidator;
import com.cinego.dto.booking.BookingRequestDTO;
import com.cinego.dto.booking.BookingResponseDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static com.cinego.common.utils.ResponseUtils.createResponse;

@WebServlet(name = "CreateBookingController", value = "/createBooking", description = "Create a new booking")
public class CreateBookingController extends HttpServlet {

    private final BookingService bookingService = new BookingService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        HttpSession session = request.getSession(false);
        try {
            BookingRequestDTO bookingRequestDTO = ObjectMapperUtil.toObject(request.getReader(), BookingRequestDTO.class);
            UserResponseDTO currentUser = (UserResponseDTO) session.getAttribute("user");
            int currentUserId = currentUser.getUserId();
            bookingRequestDTO.setUserId(currentUserId);
            BookingValidator.validateCreateBooking(bookingRequestDTO);
            BookingResponseDTO bookingResponseDTO = bookingService.createBooking(bookingRequestDTO, currentUserId);
            createResponse(response, Message.Success.BOOKING_SUCCESS, bookingResponseDTO, HttpServletResponse.SC_CREATED);
        } catch (DBException e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ApplicationException e) {
            createResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (IOException e) {
            createResponse(response, Message.Error.INVALID_JSON_REQUEST + e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}