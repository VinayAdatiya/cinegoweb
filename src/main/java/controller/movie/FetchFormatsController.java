package controller.movie;

import common.AppConstant;
import common.Message;
import common.exception.DBException;
import jakarta.servlet.annotation.WebServlet;
import model.Format;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.FormatService;

import java.io.IOException;
import java.util.List;

import static common.utils.ResponseUtils.createResponse;

@WebServlet(name = "FetchFormatsController", value = "/getFormats", description = "Get All Movie Formats List e.x. 2D,3D etc...")
public class FetchFormatsController extends HttpServlet {
    private final FormatService formatService = new FormatService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            List<Format> formats = formatService.getAllFormats();
            createResponse(response, Message.Success.FORMATS_FOUND, formats, HttpServletResponse.SC_OK);
        } catch (DBException e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            createResponse(response, Message.Error.SERVER_ERROR + e.getMessage(), null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
