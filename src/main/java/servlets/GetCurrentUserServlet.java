package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        ApiResponse apiResponse;
        HttpSession session = request.getSession();
        User user = null;

        if(session != null){
            user =(User) session.getAttribute("user");
        }


        if(user != null){
            apiResponse = new ApiResponse("User Found ",user);
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else{
            apiResponse = new ApiResponse("No User Logged In Found",null);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
