package controller;

import common.AppConstant;
import common.Message;
import common.utils.ObjectMapperUtil;
import dto.user.UserResponseDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dto.ApiResponse;

import java.io.IOException;

@WebServlet(name = "GetCurrentUserController", value = "/getCurrentUser", description = "Get Current User")
public class GetCurrentUserController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        ApiResponse apiResponse;
        HttpSession session = request.getSession(false);
        UserResponseDTO user = null;
        if (session != null) {
            user = (UserResponseDTO) session.getAttribute("user");
        }
        if (user != null) {
            apiResponse = new ApiResponse(Message.Success.USER_FOUND, user);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            apiResponse = new ApiResponse(Message.Error.USER_NOT_FOUND, null);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }
}
