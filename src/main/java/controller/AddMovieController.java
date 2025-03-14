package controller;

import common.AppConstant;
import common.Message;
import common.ObjectMapperUtil;
import common.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import model.Movie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dto.ApiResponse;
import controller.validation.MovieValidator;
import service.MovieService;
import utils.AuthenticateUtil;
import java.io.IOException;

public class AddMovieController extends HttpServlet {
    private final MovieService movieService = new MovieService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        ApiResponse apiResponse;
        try {
            AuthenticateUtil.authorize(request,Role.ROLE_SUPER_ADMIN);
            Movie movie = ObjectMapperUtil.toObject(request.getReader(), Movie.class);
            System.out.println(movie);
            MovieValidator.validateMovie(movie);
            int movieId = movieService.addMovie(movie);
            apiResponse = new ApiResponse(Message.Success.MOVIE_ADDED, movieId);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DBException e) {
            apiResponse = new ApiResponse("Database Error: " + e.getMessage(), null);
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