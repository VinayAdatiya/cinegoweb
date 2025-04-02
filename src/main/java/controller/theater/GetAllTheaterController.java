package controller.theater;

import common.AppConstant;
import common.Message;
import common.exception.ApplicationException;
import dto.theater.TheaterResponseDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.TheaterService;
import java.io.IOException;
import java.util.List;

import static common.utils.ResponseUtils.createResponse;

@WebServlet(name = "GetAllTheaterController", value = "/getAllTheaters", description = "Get All Theaters Info")
public class GetAllTheaterController extends HttpServlet {
    private final TheaterService theaterService = new TheaterService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            List<TheaterResponseDTO> theaterResponseDTOS = theaterService.getAllTheaters();
            createResponse(response, Message.Success.THEATERS_FOUND, theaterResponseDTOS, HttpServletResponse.SC_OK);
        } catch (ApplicationException e) {
            createResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
