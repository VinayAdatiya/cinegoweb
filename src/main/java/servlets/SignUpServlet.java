package servlets;

import common.AppConstant;
import common.Message;
import common.ObjectMapperUtil;
import common.exception.ApplicationException;
import common.exception.DBException;
import dao.AddressDao;
import dao.UserDao;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ApiResponse;
import servlets.validation.SignUpValidator;

import java.io.IOException;

public class SignUpServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        ApiResponse apiResponse;
        try {
            // Parse JSON request body into User object
            User user = ObjectMapperUtil.toObject(request.getReader(), User.class);
            SignUpValidator.validateSignup(user);
            // Insert address first to get addressId
            int addressId = AddressDao.insertAddress(user.getAddress());
            // Updating user with generated addressId
            user.getAddress().setAddressId(addressId);
            UserDao.registerUser(user);
            apiResponse = new ApiResponse(Message.Success.SIGNUP_SUCCESS, null);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DBException e) {
            apiResponse = new ApiResponse("", null);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ApplicationException e) {
            apiResponse = new ApiResponse(e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (IOException e) {
            apiResponse = new ApiResponse("Invalid JSON request: " + e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            apiResponse = new ApiResponse("Server error: " + e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }
}
