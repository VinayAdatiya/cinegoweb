package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.AppConstant;
import common.Message;
import common.ObjectMapperUtil;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ApiResponse;

import java.io.IOException;

public class GetCurrentUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        ApiResponse apiResponse;
        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
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
