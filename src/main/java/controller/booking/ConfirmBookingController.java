package controller.booking;

import common.AppConstant;
import common.Message;
import common.exception.ApplicationException;
import common.exception.DBException;
import common.utils.ObjectMapperUtil;
import controller.validation.BookingValidator;
import dto.ApiResponse;
import dto.booking.BookingRequestDTO;
import dto.booking.BookingResponseDTO;
import dto.user.UserResponseDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.BookingService;

import java.io.IOException;

@WebServlet(name = "ConfirmBookingController", value = "/confirmBooking", description = "Confirm an existing booking")
public class ConfirmBookingController extends HttpServlet {

    private final BookingService bookingService = new BookingService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        HttpSession session = request.getSession(false);
        ApiResponse apiResponse;
        try {
            UserResponseDTO currentUser = (UserResponseDTO) session.getAttribute("user");
            int currentUserId = currentUser.getUserId();
            BookingRequestDTO bookingRequestDTO = ObjectMapperUtil.toObject(request.getReader(), BookingRequestDTO.class);
            BookingValidator.validateConfirmBooking(bookingRequestDTO);
            BookingResponseDTO bookingResponseDTO = bookingService.confirmBooking(bookingRequestDTO, currentUserId);
            apiResponse = new ApiResponse(Message.Success.BOOKING_CONFIRMED, bookingResponseDTO);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DBException e) {
            e.printStackTrace();
            apiResponse = new ApiResponse(Message.Error.INTERNAL_ERROR, null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ApplicationException e) {
            apiResponse = new ApiResponse(e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (IOException e) {
            apiResponse = new ApiResponse(Message.Error.INVALID_JSON_REQUEST, null);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            apiResponse = new ApiResponse(Message.Error.SERVER_ERROR + e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }
}