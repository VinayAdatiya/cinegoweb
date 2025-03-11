// servlets/AddMovieLanguagesServlet.java (Updated)
package servlets;

import common.AppConstant;
import common.Message;
import common.ObjectMapperUtil;
import common.exception.ApplicationException;
import common.exception.DBException;
import dao.MovieDao;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ApiResponse;
import servlets.validation.MovieLanguagesValidator;
import utils.DBConnection;
import java.io.IOException;
import java.sql.Connection;

public class AddMovieLanguagesServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);

        ApiResponse apiResponse;
        try {
            MovieLanguagesValidator.MovieLanguageList movieLanguageList = ObjectMapperUtil.toObject(request.getReader(), MovieLanguagesValidator.MovieLanguageList.class);
            MovieLanguagesValidator.validateMovieLanguageList(movieLanguageList);

            try (Connection connection = DBConnection.INSTANCE.getConnection()) {
                MovieDao.addMovieLanguages(movieLanguageList.getMovieId(), movieLanguageList.getLanguageIds(), connection);
                apiResponse = new ApiResponse(Message.Success.MOVIE_LANGUAGES_ADDED, null);
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