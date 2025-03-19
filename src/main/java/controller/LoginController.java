package controller;

import common.AppConstant;
import common.Message;
import common.utils.ObjectMapperUtil;
import common.exception.ApplicationException;
import common.exception.DBException;
import dto.user.UserResponseDTO;
import model.User;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dto.ApiResponse;
import controller.validation.LoginValidator;
import service.UserService;

import java.io.IOException;

public class LoginController extends HttpServlet {
    private final UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        ApiResponse apiResponse;
        try {
            User userRequest = ObjectMapperUtil.toObject(request.getReader(), User.class);
            LoginValidator.validateLogin(userRequest);
            UserResponseDTO userResponseDTO = userService.authenticateUser(userRequest.getEmail(), userRequest.getPassword());
            HttpSession session = request.getSession();
            session.setAttribute("user", userResponseDTO);
            apiResponse = new ApiResponse(Message.Success.LOGIN_SUCCESS, userResponseDTO.getRole().getRoleId());
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DBException e) {
            apiResponse = new ApiResponse(Message.Error.INTERNAL_ERROR, null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ApplicationException e) {
            apiResponse = new ApiResponse(e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            apiResponse = new ApiResponse(Message.Error.INTERNAL_ERROR, null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }
}
