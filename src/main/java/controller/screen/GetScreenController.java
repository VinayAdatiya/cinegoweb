package controller.screen;

import common.AppConstant;
import common.Message;
import common.exception.ApplicationException;
import common.exception.DBException;
import dto.screen.ScreenResponseDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ScreenService;
import java.io.IOException;

import static common.utils.ResponseUtils.createResponse;

@WebServlet(name = "GetScreenByIdController", value = "/getScreenById", description = "Get Screen by ID")
public class GetScreenController extends HttpServlet {

    private final ScreenService screenService = new ScreenService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            int screenId = Integer.parseInt(request.getParameter("screenId"));
            ScreenResponseDTO screenResponseDTO = screenService.getScreenById(screenId);
            createResponse(response, Message.Success.RECORD_FOUND, screenResponseDTO, HttpServletResponse.SC_OK);
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