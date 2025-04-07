package com.cinego.controller.theater;

import com.cinego.common.AppConstant;
import com.cinego.common.Message;
import com.cinego.common.exception.ApplicationException;
import com.cinego.common.utils.ResponseUtils;
import com.cinego.dto.theater.TheaterResponseDTO;
import com.cinego.service.TheaterService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetAllTheaterController", value = "/getAllTheaters", description = "Get All Theaters Info")
public class GetAllTheaterController extends HttpServlet {
    private final TheaterService theaterService = new TheaterService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            List<TheaterResponseDTO> theaterResponseDTOS = theaterService.getAllTheaters();
            ResponseUtils.createResponse(response, Message.Success.THEATERS_FOUND, theaterResponseDTOS, HttpServletResponse.SC_OK);
        } catch (ApplicationException e) {
            ResponseUtils.createResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            ResponseUtils.createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
