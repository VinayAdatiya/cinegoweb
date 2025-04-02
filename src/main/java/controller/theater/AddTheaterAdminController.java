package controller.theater;

import common.AppConstant;
import common.Message;
import common.utils.ObjectMapperUtil;
import common.enums.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import dto.user.UserSignUpDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import controller.validation.SignUpValidator;
import service.UserService;
import common.utils.AuthenticateUtil;

import java.io.IOException;

import static common.utils.ResponseUtils.createResponse;

@WebServlet(name = "AddTheaterAdminController", value = "/addTheaterAdmin", description = "OnBoard Theater Admin By SuperAdmin")
public class AddTheaterAdminController extends HttpServlet {
    private final UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            AuthenticateUtil.authorize(request, Role.ROLE_SUPER_ADMIN);
            UserSignUpDTO userSignUpDTO = ObjectMapperUtil.toObject(request.getReader(), UserSignUpDTO.class);
            SignUpValidator.validateSignup(userSignUpDTO, userService);
            // Set roleId to 2 for Theater Admin
            userSignUpDTO.setRole(Role.ROLE_THEATER_ADMIN);
            userService.registerUser(userSignUpDTO);
            createResponse(response, Message.Success.THEATER_ADMIN_REGISTERED, null, HttpServletResponse.SC_CREATED);
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