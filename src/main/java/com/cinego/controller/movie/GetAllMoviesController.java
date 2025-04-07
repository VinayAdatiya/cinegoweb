package com.cinego.controller.movie;

import com.cinego.common.AppConstant;
import com.cinego.common.Message;
import com.cinego.common.exception.ApplicationException;
import com.cinego.common.utils.ResponseUtils;
import com.cinego.dto.movie.MovieDTO;
import com.cinego.service.MovieService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetAllMoviesController", value = "/getAllMovies", description = "Get All Movies Details")
public class GetAllMoviesController extends HttpServlet {
    private final MovieService movieService = new MovieService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            List<MovieDTO> movieResponseDTOs = movieService.getAllMovies();
            ResponseUtils.createResponse(response, Message.Success.MOVIES_FOUND, movieResponseDTOs, HttpServletResponse.SC_OK);
        } catch (ApplicationException e) {
            ResponseUtils.createResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            ResponseUtils.createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}