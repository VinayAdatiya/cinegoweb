package controller.show;

import common.AppConstant;
import common.Message;
import common.exception.ApplicationException;
import common.exception.DBException;
import dto.show.ShowResponseDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ShowService;
import java.io.IOException;

import static common.utils.ResponseUtils.createResponse;

@WebServlet(name = "GetShowController", value = "/getShow", description = "Get Show By Id")
public class GetShowController extends HttpServlet {

    private final ShowService showService = new ShowService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            int showId = Integer.parseInt(request.getParameter("showId"));
            ShowResponseDTO showResponseDTO = showService.getShowById(showId);
            createResponse(response, Message.Success.RECORD_FOUND, showResponseDTO, HttpServletResponse.SC_OK);
        } catch (DBException e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NumberFormatException e) {
            throw new ApplicationException(Message.Error.INVALID_ID);
        } catch (ApplicationException e) {
            createResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}