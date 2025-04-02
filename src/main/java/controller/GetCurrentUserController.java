package controller;

import common.AppConstant;
import common.Message;
import dto.user.UserResponseDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static common.utils.ResponseUtils.createResponse;

@WebServlet(name = "GetCurrentUserController", value = "/getCurrentUser", description = "Get Current User")
public class GetCurrentUserController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        HttpSession session = request.getSession(false);
        UserResponseDTO user = null;
        if (session != null) {
            user = (UserResponseDTO) session.getAttribute("user");
        }
        if (user != null) {
            createResponse(response, Message.Success.USER_FOUND, user, HttpServletResponse.SC_OK);
        } else {
            createResponse(response, Message.Error.USER_NOT_FOUND, null, HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
