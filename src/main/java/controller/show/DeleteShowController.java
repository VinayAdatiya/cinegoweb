package controller.show;

import common.AppConstant;
import common.Message;
import common.enums.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import common.utils.AuthenticateUtil;
import common.utils.ObjectMapperUtil;
import dto.ApiResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ShowService;

import java.io.IOException;

@WebServlet(name = "DeleteShowController", value = "/deleteShow", description = "Delete Show")
public class DeleteShowController extends HttpServlet {

    private final ShowService showService = new ShowService();

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        ApiResponse apiResponse;
        try {
            AuthenticateUtil.authorize(request, Role.ROLE_THEATER_ADMIN);
            int showId = Integer.parseInt(request.getParameter("showId"));
            showService.deleteShow(showId);
            apiResponse = new ApiResponse(Message.Success.RECORD_DELETED, null);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            apiResponse = new ApiResponse(Message.Error.INVALID_ID, null);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (DBException e) {
            apiResponse = new ApiResponse(Message.Error.INTERNAL_ERROR, null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ApplicationException e) {
            apiResponse = new ApiResponse(e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            apiResponse = new ApiResponse(Message.Error.INTERNAL_ERROR, null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }
}