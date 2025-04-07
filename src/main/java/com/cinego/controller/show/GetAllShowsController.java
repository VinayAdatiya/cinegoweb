package com.cinego.controller.show;

import com.cinego.common.AppConstant;
import com.cinego.common.Message;
import com.cinego.common.utils.ResponseUtils;
import com.cinego.dto.show.ShowResponseDTO;
import com.cinego.service.ShowService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetAllShowsController", value = "/getAllShows", description = "Get All Shows")
public class GetAllShowsController extends HttpServlet {

    private final ShowService showService = new ShowService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            List<ShowResponseDTO> showResponseDTOList = showService.getAllShows();
            ResponseUtils.createResponse(response, Message.Success.SHOWS_FOUND, showResponseDTOList, HttpServletResponse.SC_OK);
        } catch (Exception e) {
            ResponseUtils.createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}