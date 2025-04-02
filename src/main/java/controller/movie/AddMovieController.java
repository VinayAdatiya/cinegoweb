package controller.movie;

import common.AppConstant;
import common.Message;
import common.enums.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import common.utils.AuthenticateUtil;
import common.utils.ObjectMapperUtil;
import controller.validation.MovieValidator;
import dto.movie.MovieRequestDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.MovieService;

import java.io.IOException;

import static common.utils.ResponseUtils.createResponse;

@WebServlet(name = "AddMovieController", value = "/addMovie", description = "Add New Movie by SuperAdmin")
public class AddMovieController extends HttpServlet {
    private final MovieService movieService = new MovieService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            AuthenticateUtil.authorize(request, Role.ROLE_SUPER_ADMIN);
            MovieRequestDTO movieRequestDTO = ObjectMapperUtil.toObject(request.getReader(), MovieRequestDTO.class);
            MovieValidator.validateMovie(movieRequestDTO);
            movieService.addMovie(movieRequestDTO);
            createResponse(response, Message.Success.MOVIE_ADDED, null, HttpServletResponse.SC_CREATED);
        } catch (DBException e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ApplicationException e) {
            createResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (IOException e) {
            createResponse(response, Message.Error.INVALID_JSON_REQUEST + e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}