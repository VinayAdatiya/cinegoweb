package controller;

import common.AppConstant;
import common.Message;
import common.utils.ObjectMapperUtil;
import dto.ApiResponse;
import dto.theater.TheaterResponseDTO;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.TheaterService;

import java.io.IOException;
import java.util.List;

public class GetAllTheaterController extends HttpServlet {
    private final TheaterService theaterService = new TheaterService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        ApiResponse apiResponse;
        try {
            List<TheaterResponseDTO> theaterResponseDTOS = theaterService.getAllTheaters();
            apiResponse = new ApiResponse(Message.Success.THEATERS_FOUND, theaterResponseDTOS);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            apiResponse = new ApiResponse(Message.Error.INTERNAL_ERROR, null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }
}
