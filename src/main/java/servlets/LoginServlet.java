package servlets;

import common.Message;
import common.ObjectMapperUtil;
import dao.UserDao;
import entity.User;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ApiResponse;
import servlets.validation.LoginValidator;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ApiResponse apiResponse;
        try {
            // Parse JSON request body into User object
            User userRequest = ObjectMapperUtil.toObject(request.getReader(), User.class);
            LoginValidator.validateLogin(userRequest);
            User user = UserDao.authenticateUser(userRequest.getEmail(), userRequest.getPassword());
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            apiResponse = new ApiResponse(Message.Success.LOGIN_SUCCESS, user.getRoleId());
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            apiResponse = new ApiResponse("Server error: " + e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }
}
