package controller;

import common.AppConstant;
import common.Message;
import common.ObjectMapperUtil;
import common.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import model.Theater;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dto.ApiResponse;
import controller.validation.TheaterValidator;
import service.TheaterService;
import utils.AuthenticateUtil;
import java.io.IOException;

public class AddTheaterController extends HttpServlet {
    private final TheaterService theaterService = new TheaterService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        ApiResponse apiResponse;
        try {
            AuthenticateUtil.authorize(request, Role.ROLE_SUPER_ADMIN);
            Theater theater = ObjectMapperUtil.toObject(request.getReader(), Theater.class);
            TheaterValidator.validateTheater(theater);
            theaterService.addTheater(theater);
            apiResponse = new ApiResponse(Message.Success.THEATER_SUCCESS, null);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DBException e) {
            apiResponse = new ApiResponse(Message.Error.THEATER_FAILED, null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ApplicationException e) {
            apiResponse = new ApiResponse(e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (IOException e) {
            apiResponse = new ApiResponse("Invalid JSON request: " + e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            apiResponse = new ApiResponse("Server error: " + e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }
}
