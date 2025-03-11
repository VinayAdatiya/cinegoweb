package servlets;

import common.AppConstant;
import common.Message;
import common.ObjectMapperUtil;
import common.exception.ApplicationException;
import common.exception.DBException;
import dao.TheaterDao;
import entity.Theater;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ApiResponse;
import servlets.validation.TheaterValidator;
import java.io.IOException;

public class AddTheaterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        ApiResponse apiResponse;
        try {
            Theater theater = ObjectMapperUtil.toObject(request.getReader(), Theater.class);
            TheaterValidator.validateTheater(theater);
            TheaterDao.addTheater(theater);
            apiResponse = new ApiResponse(Message.Success.THEATER_SUCCESS, null);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DBException e) {
            apiResponse = new ApiResponse("Database error: " + e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
