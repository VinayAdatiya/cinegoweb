package controller;

import common.AppConstant;
import common.Message;
import common.ObjectMapperUtil;
import common.exception.ApplicationException;
import common.exception.DBException;
import dao.UserDao;
import entity.User;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ApiResponse;
import controller.validation.LoginValidator;
import java.io.IOException;

public class LoginController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        ApiResponse apiResponse;
        try {
            User userRequest = ObjectMapperUtil.toObject(request.getReader(), User.class);
            LoginValidator.validateLogin(userRequest);
            User user = UserDao.authenticateUser(userRequest.getEmail(), userRequest.getPassword());
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            apiResponse = new ApiResponse(Message.Success.LOGIN_SUCCESS, user.getRole().getRoleId());
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DBException e) {
            apiResponse = new ApiResponse("Database error: " + e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ApplicationException e) {
            apiResponse = new ApiResponse(e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            apiResponse = new ApiResponse("Server error: " + e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }
}
