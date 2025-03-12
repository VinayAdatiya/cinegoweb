// servlets/AddMovieGenresServlet.java (Updated)
package controller;

import common.AppConstant;
import common.Message;
import common.ObjectMapperUtil;
import common.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import dao.MovieDao;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ApiResponse;
import controller.validation.MovieFormatsValidator;
import utils.AuthenticateUtil;
import utils.DBConnection;
import java.io.IOException;
import java.sql.Connection;

public class AddMovieFormatsController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        AuthenticateUtil.authorize(request, Role.ROLE_SUPER_ADMIN);
        ApiResponse apiResponse;
        try {
            MovieFormatsValidator.MovieFormatList movieFormatList = ObjectMapperUtil.toObject(request.getReader(), MovieFormatsValidator.MovieFormatList.class);
            MovieFormatsValidator.validateMovieFormatList(movieFormatList);

            try (Connection connection = DBConnection.INSTANCE.getConnection()) {
                MovieDao.addMovieFormats(movieFormatList.getMovieId(), movieFormatList.getFormatIds(), connection);
                apiResponse = new ApiResponse(Message.Success.MOVIE_FORMATS_ADDED, null);
                response.setStatus(HttpServletResponse.SC_CREATED);
            } catch (DBException e) {
                apiResponse = new ApiResponse("Database Error: " + e.getMessage(), null);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } catch (ClassNotFoundException e) {
                apiResponse = new ApiResponse("Class not found: " + e.getMessage(), null);
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

        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }
}