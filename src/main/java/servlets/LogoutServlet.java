package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.AppConstant;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ApiResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);

        ObjectMapper objectMapper = new ObjectMapper();
        ApiResponse apiResponse;

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            apiResponse = new ApiResponse("Logged Out Successfully !!!", null);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            apiResponse = new ApiResponse("Logged Out Failed !!!", null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
