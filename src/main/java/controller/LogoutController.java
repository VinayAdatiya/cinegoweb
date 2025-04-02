package controller;

import common.AppConstant;
import common.Message;
import common.exception.ApplicationException;
import controller.validation.LogoutValidator;
import dto.user.UserResponseDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import static common.utils.ResponseUtils.createResponse;

@WebServlet(name = "LoginController", value = "/logout", description = "Logout & Invalidate Session")
public class LogoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                UserResponseDTO userResponseDTO = (UserResponseDTO) session.getAttribute("user");
                LogoutValidator.validateLogout(userResponseDTO);
                session.invalidate();
                createResponse(response, Message.Success.LOGOUT_SUCCESS, null, HttpServletResponse.SC_OK);
            } else {
                throw new ApplicationException(Message.Error.SESSION_EXPIRED);
            }
        } catch (ApplicationException e) {
            createResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
