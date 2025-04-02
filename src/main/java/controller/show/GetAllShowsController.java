package controller.show;

import common.AppConstant;
import common.Message;
import dto.show.ShowResponseDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ShowService;
import java.io.IOException;
import java.util.List;

import static common.utils.ResponseUtils.createResponse;

@WebServlet(name = "GetAllShowsController", value = "/getAllShows", description = "Get All Shows")
public class GetAllShowsController extends HttpServlet {

    private final ShowService showService = new ShowService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            List<ShowResponseDTO> showResponseDTOList = showService.getAllShows();
            createResponse(response, Message.Success.SHOWS_FOUND, showResponseDTOList, HttpServletResponse.SC_OK);
        } catch (Exception e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}