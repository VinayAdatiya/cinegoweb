package controller;

import common.AppConstant;
import common.Message;
import common.utils.ObjectMapperUtil;
import common.exception.ApplicationException;
import common.exception.DBException;
import dto.user.UserResponseDTO;
import jakarta.servlet.annotation.WebServlet;
import model.User;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import controller.validation.LoginValidator;
import service.UserService;
import java.io.IOException;

import static common.utils.ResponseUtils.createResponse;

@WebServlet(name = "LoginController", value = "/login", description = "Common Login API for all Users")
public class LoginController extends HttpServlet {
    private final UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            User userRequest = ObjectMapperUtil.toObject(request.getReader(), User.class);
            LoginValidator.validateLogin(userRequest);
            UserResponseDTO userResponseDTO = userService.authenticateUser(userRequest.getEmail(), userRequest.getPassword());
            HttpSession session = request.getSession();
            session.setAttribute("user", userResponseDTO);
            createResponse(response, Message.Success.LOGIN_SUCCESS, userResponseDTO.getRole().getRoleId(), HttpServletResponse.SC_OK);
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
