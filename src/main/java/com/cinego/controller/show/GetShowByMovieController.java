package com.cinego.controller.show;

import com.cinego.common.AppConstant;
import com.cinego.common.Message;
import com.cinego.common.exception.ApplicationException;
import com.cinego.common.exception.DBException;
import com.cinego.common.utils.ResponseUtils;
import com.cinego.dto.show.ShowResponseDTO;
import com.cinego.service.ShowService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetShowByMovieController", value = "/getShowByMovie", description = "Get Show By MovieId")
public class GetShowByMovieController extends HttpServlet {
    private final ShowService showService = new ShowService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            int movieId = Integer.parseInt(request.getParameter("movieId"));
            List<ShowResponseDTO> showResponseDTOs = showService.getShowByMovie(movieId);
            ResponseUtils.createResponse(response, Message.Success.RECORD_FOUND, showResponseDTOs, HttpServletResponse.SC_OK);
        } catch (DBException e) {
            e.printStackTrace();
            ResponseUtils.createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NumberFormatException e) {
            throw new ApplicationException(Message.Error.INVALID_ID);
        } catch (ApplicationException e) {
            ResponseUtils.createResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtils.createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
