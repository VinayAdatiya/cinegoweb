package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.exception.ApplicationException;
import dao.AddressDao;
import dao.UserDao;
import entity.User;
import model.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlets.validation.SignUpValidator;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        ApiResponse apiResponse;

        try {
            // Parse JSON request body into User object
            User user = objectMapper.readValue(request.getReader(), User.class);
            SignUpValidator.validateSignup(user);
            // Insert address first to get addressId
            int addressId = AddressDao.insertAddress(user.getAddress());
            // Updating user with generated addressId
            user.getAddress().setAddressId(addressId);
            boolean success = UserDao.registerUser(user);

            if (success) {
                apiResponse = new ApiResponse("User registered successfully", null);
                response.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                apiResponse = new ApiResponse("Failed to register user", null);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
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
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
