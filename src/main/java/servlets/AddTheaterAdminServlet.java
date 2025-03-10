package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.AppConstant;
import common.Message;
import common.ObjectMapperUtil;
import common.Role;
import common.exception.ApplicationException;
import dao.AddressDao;
import dao.UserDao;
import entity.User;
import model.ApiResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlets.validation.SignUpValidator;

import java.io.IOException;

public class AddTheaterAdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
            // Set roleId to 2 for Theater Admin
            user.setRole(Role.ROLE_THEATER_ADMIN);
            UserDao.registerUser(user);
            apiResponse = new ApiResponse(Message.Success.THEATER_ADMIN_REGISTERED, null);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (ApplicationException e) {
            apiResponse = new ApiResponse(Message.Error.THEATER_ADMIN_FAILED, null);
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