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

@WebServlet(name = "GetShowController", value = "/getShow", description = "Get Show By Id")
public class GetShowController extends HttpServlet {

    private final ShowService showService = new ShowService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        ApiResponse apiResponse;
        try {
            int showId = Integer.parseInt(request.getParameter("showId"));
            ShowResponseDTO showResponseDTO = showService.getShowById(showId);
            if (showResponseDTO != null) {
                apiResponse = new ApiResponse(Message.Success.RECORD_FOUND, showResponseDTO);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                apiResponse = new ApiResponse(Message.Error.INVALID_ID, null);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (DBException e) {
            apiResponse = new ApiResponse(Message.Error.INTERNAL_ERROR, null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NumberFormatException e) {
            apiResponse = new ApiResponse(Message.Error.INVALID_ID, null);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }
}