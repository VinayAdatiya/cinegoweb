package controller.screen;

import common.AppConstant;
import common.Message;
import common.enums.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import common.utils.AuthenticateUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ScreenService;

import java.io.IOException;

import static common.utils.ResponseUtils.createResponse;

@WebServlet(name = "DeleteScreenController", value = "/deleteScreen", description = "Delete Screen by Theater Admin")
public class DeleteScreenController extends HttpServlet {

    private final ScreenService screenService = new ScreenService();

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            AuthenticateUtil.authorize(request, Role.ROLE_THEATER_ADMIN);
            int screenId = Integer.parseInt(request.getParameter("screenId"));
            screenService.deleteScreen(screenId);
            createResponse(response, Message.Success.RECORD_DELETED, null, HttpServletResponse.SC_OK);
        } catch (DBException e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ApplicationException e) {
            createResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}