package com.cinego.controller.movie;

import com.cinego.common.AppConstant;
import com.cinego.common.Message;
import com.cinego.common.enums.Role;
import com.cinego.common.exception.ApplicationException;
import com.cinego.common.exception.DBException;
import com.cinego.common.utils.ResponseUtils;
import com.cinego.controller.validation.MovieValidator;
import com.cinego.dto.movie.MovieRequestDTO;
import com.cinego.service.MovieService;
import com.cinego.common.utils.AuthenticateUtil;
import com.cinego.common.utils.ObjectMapperUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "UpdateMovieController", value = "/updateMovie", description = "Update Movie Details restricted to SuperAdmin")
public class UpdateMovieController extends HttpServlet {
    private final MovieService movieService = new MovieService();

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            AuthenticateUtil.authorize(request, Role.ROLE_SUPER_ADMIN);
            MovieRequestDTO movieRequestDTO = ObjectMapperUtil.toObject(request.getReader(), MovieRequestDTO.class);
            MovieValidator.validateMovie(movieRequestDTO);
            movieService.updateMovie(movieRequestDTO);
            ResponseUtils.createResponse(response, Message.Success.RECORD_UPDATED, null, HttpServletResponse.SC_OK);
        } catch (DBException e) {
            ResponseUtils.createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ApplicationException e) {
            ResponseUtils.createResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (IOException e) {
            ResponseUtils.createResponse(response, Message.Error.INVALID_JSON_REQUEST + e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            ResponseUtils.createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}