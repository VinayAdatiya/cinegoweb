package controller.movie;

import common.AppConstant;
import common.Message;
import common.exception.ApplicationException;
import common.exception.DBException;
import common.utils.ObjectMapperUtil;
import dto.ApiResponse;
import dto.movie.MovieDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.MovieService;

import java.io.IOException;

@WebServlet(name = "GetMovieController" , value = "/fetchMovie" , description = "Fetch Particular Movie by movieId")
public class GetMovieController extends HttpServlet {
    private final MovieService movieService = new MovieService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        ApiResponse apiResponse;
        try {
            int movieId;
            try {
                movieId = Integer.parseInt(request.getParameter("movieId"));
            } catch (NumberFormatException e) {
                throw new ApplicationException(Message.Error.INVALID_ID);
            }
            MovieDTO movieResponseDTO = movieService.getMovieById(movieId);
            if (movieResponseDTO != null) {
                apiResponse = new ApiResponse(Message.Success.RECORD_FOUND, movieResponseDTO);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                apiResponse = new ApiResponse(Message.Error.NO_RECORD_FOUND, null);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (DBException e) {
            apiResponse = new ApiResponse(Message.Error.INTERNAL_ERROR, null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            apiResponse = new ApiResponse("Server Error : " + e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }
}