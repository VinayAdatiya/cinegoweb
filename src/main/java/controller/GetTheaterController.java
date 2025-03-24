package controller;

import common.AppConstant;
import common.Message;
import common.exception.ApplicationException;
import common.exception.DBException;
import common.utils.ObjectMapperUtil;
import dto.ApiResponse;
import dto.theater.TheaterResponseDTO;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.TheaterService;

import java.io.IOException;

public class GetTheaterController extends HttpServlet {
    private final TheaterService theaterService = new TheaterService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        ApiResponse apiResponse;
        try {
            int theaterId;
            try {
                theaterId = Integer.parseInt(request.getParameter("theaterId"));
            } catch (NumberFormatException e) {
                throw new ApplicationException(Message.Error.INVALID_ID);
            }
            TheaterResponseDTO theaterResponseDTO = theaterService.getTheaterById(theaterId);
            if (theaterResponseDTO != null) {
                apiResponse = new ApiResponse(Message.Success.RECORD_FOUND, theaterResponseDTO);
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
