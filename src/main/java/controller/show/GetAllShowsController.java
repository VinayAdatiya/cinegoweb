package controller.show;

import common.AppConstant;
import common.Message;
import common.exception.DBException;
import common.utils.ObjectMapperUtil;
import dto.ApiResponse;
import dto.show.ShowResponseDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ShowService;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetAllShowsController", value = "/getAllShows", description = "Get All Shows")
public class GetAllShowsController extends HttpServlet {

    private final ShowService showService = new ShowService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        ApiResponse apiResponse;
        try {
            List<ShowResponseDTO> showResponseDTOList = showService.getAllShows();
            apiResponse = new ApiResponse(Message.Success.SHOWS_FOUND, showResponseDTOList);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DBException e) {
            apiResponse = new ApiResponse(Message.Error.INTERNAL_ERROR, null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }
}