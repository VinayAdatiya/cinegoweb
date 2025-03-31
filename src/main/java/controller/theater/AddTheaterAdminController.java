package controller.theater;

import common.AppConstant;
import common.Message;
import common.utils.ObjectMapperUtil;
import common.enums.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import dto.user.UserSignUpDTO;
import dto.ApiResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import controller.validation.SignUpValidator;
import service.UserService;
import common.utils.AuthenticateUtil;
import java.io.IOException;

@WebServlet(name = "AddTheaterAdminController" , value = "/addTheaterAdmin" , description = "OnBoard Theater Admin By SuperAdmin")
public class AddTheaterAdminController extends HttpServlet {
    private final UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        ApiResponse apiResponse;
        try {
            AuthenticateUtil.authorize(request, Role.ROLE_SUPER_ADMIN);
            UserSignUpDTO userSignUpDTO = ObjectMapperUtil.toObject(request.getReader(), UserSignUpDTO.class);
            SignUpValidator.validateSignup(userSignUpDTO, userService);
            // Set roleId to 2 for Theater Admin
            userSignUpDTO.setRole(Role.ROLE_THEATER_ADMIN);
            userService.registerUser(userSignUpDTO);
            apiResponse = new ApiResponse(Message.Success.THEATER_ADMIN_REGISTERED, null);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DBException e) {
            apiResponse = new ApiResponse(Message.Error.THEATER_ADMIN_FAILED, null);
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