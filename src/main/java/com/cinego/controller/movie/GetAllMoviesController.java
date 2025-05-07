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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "GetAllMoviesController", value = "/getAllMovies", description = "Get All Movies Details")
public class GetAllMoviesController extends HttpServlet {
    private final MovieService movieService = new MovieService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            int page = Optional.ofNullable(request.getParameter("page")).map(Integer::parseInt).orElse(1);
            int pageSize = Optional.ofNullable(request.getParameter("pageSize")).map(Integer::parseInt).orElse(10);
            String sortBy = Optional.ofNullable(request.getParameter("sortBy")).orElse("movieReleaseDate");
            String sortOrder = Optional.ofNullable(request.getParameter("sortOrder")).orElse("DESC");
            List<String> languages = Optional.ofNullable(request.getParameterValues("languages")).map(Arrays::asList).orElse(null);
            List<String> formats = Optional.ofNullable(request.getParameterValues("formats")).map(Arrays::asList).orElse(null);
            List<String> genres = Optional.ofNullable(request.getParameterValues("genres")).map(Arrays::asList).orElse(null);
            List<MovieDTO> movieResponseDTOs = movieService.getAllMovies(page, pageSize, sortBy, sortOrder, languages, formats, genres);
            ResponseUtils.createResponse(response, Message.Success.MOVIES_FOUND, movieResponseDTOs, HttpServletResponse.SC_OK);
        } catch (ApplicationException e) {
            e.printStackTrace();
            ResponseUtils.createResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtils.createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}