package controller.theater;

import common.AppConstant;
import common.Message;
import common.utils.ObjectMapperUtil;
import common.enums.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import dto.theater.TheaterRequestDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dto.ApiResponse;
import controller.validation.TheaterValidator;
import service.TheaterService;
import common.utils.AuthenticateUtil;

import java.io.IOException;

@WebServlet(name = "AddTheaterController" , value = "/addTheater" , description = "Setup New Theater TheaterAdmin Credentials are required")
public class AddTheaterController extends HttpServlet {
    private final TheaterService theaterService = new TheaterService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        ApiResponse apiResponse;
        try {
            AuthenticateUtil.authorize(request, Role.ROLE_SUPER_ADMIN);
            TheaterRequestDTO theaterRequestDTO = ObjectMapperUtil.toObject(request.getReader(), TheaterRequestDTO.class);
            TheaterValidator.validateTheater(theaterRequestDTO);
            theaterService.addTheater(theaterRequestDTO);
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
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            apiResponse = new ApiResponse("Server error: " + e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }
}
