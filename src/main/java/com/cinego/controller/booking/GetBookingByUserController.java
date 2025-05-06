package com.cinego.controller.booking;

import com.cinego.common.AppConstant;
import com.cinego.common.Message;
import com.cinego.common.enums.Role;
import com.cinego.common.exception.ApplicationException;
import com.cinego.common.exception.DBException;
import com.cinego.common.utils.AuthenticateUtil;
import com.cinego.dto.booking.BookingResponseDTO;
import com.cinego.service.BookingService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static com.cinego.common.utils.ResponseUtils.createResponse;

@WebServlet(name = "GetBookingByUserController", value = "/getBookingByUser", description = "Get All Booking Details By UserId")
public class GetBookingByUserController extends HttpServlet {
    private final BookingService bookingService = new BookingService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            AuthenticateUtil.authorizeRole(request, List.of(Role.ROLE_CUSTOMER, Role.ROLE_SUPER_ADMIN));
            int userId = Integer.parseInt(request.getParameter("userId"));
            List<BookingResponseDTO> bookingResponseDTOs = bookingService.getBookingByUser(userId);
            createResponse(response, Message.Success.RECORD_FOUND, bookingResponseDTOs, HttpServletResponse.SC_OK);
        } catch (DBException e) {
            e.printStackTrace();
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
