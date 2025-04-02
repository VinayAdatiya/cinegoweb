package controller.movie;

import common.AppConstant;
import common.Message;
import common.exception.ApplicationException;
import common.exception.DBException;
import dto.movie.MovieDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.MovieService;
import java.io.IOException;

import static common.utils.ResponseUtils.createResponse;

@WebServlet(name = "GetMovieController", value = "/fetchMovie", description = "Fetch Particular Movie by movieId")
public class GetMovieController extends HttpServlet {
    private final MovieService movieService = new MovieService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            int movieId = Integer.parseInt(request.getParameter("movieId"));
            MovieDTO movieResponseDTO = movieService.getMovieById(movieId);
            createResponse(response, Message.Success.RECORD_FOUND, movieResponseDTO, HttpServletResponse.SC_OK);
        } catch (DBException e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NumberFormatException e) {
            throw new ApplicationException(Message.Error.INVALID_ID);
        } catch (ApplicationException e) {
            createResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}