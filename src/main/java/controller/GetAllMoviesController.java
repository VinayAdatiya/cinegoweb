package controller;

import common.AppConstant;
import common.Message;
import common.exception.DBException;
import common.utils.ObjectMapperUtil;
import dto.ApiResponse;
import dto.movie.MovieResponseDTO;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.MovieService;

import java.io.IOException;
import java.util.List;

public class GetAllMoviesController extends HttpServlet {
    private final MovieService movieService = new MovieService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        ApiResponse apiResponse;
        try {
            List<MovieResponseDTO> movieResponseDTOs = movieService.getAllMovies();
            apiResponse = new ApiResponse(Message.Success.MOVIES_FOUND, movieResponseDTOs);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            apiResponse = new ApiResponse(Message.Error.INTERNAL_ERROR, null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }
}