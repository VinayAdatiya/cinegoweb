package controller.movie;

import common.AppConstant;
import common.Message;
import common.exception.ApplicationException;
import dto.movie.MovieDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.MovieService;
import java.io.IOException;
import java.util.List;

import static common.utils.ResponseUtils.createResponse;

@WebServlet(name = "GetAllMoviesController", value = "/getAllMovies", description = "Get All Movies Details")
public class GetAllMoviesController extends HttpServlet {
    private final MovieService movieService = new MovieService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            List<MovieDTO> movieResponseDTOs = movieService.getAllMovies();
            createResponse(response, Message.Success.MOVIES_FOUND, movieResponseDTOs, HttpServletResponse.SC_OK);
        } catch (ApplicationException e) {
            createResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}