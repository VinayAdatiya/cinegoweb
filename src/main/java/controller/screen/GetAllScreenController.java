package controller.screen;

import common.AppConstant;
import common.Message;
import common.exception.ApplicationException;
import common.utils.ObjectMapperUtil;
import dto.ApiResponse;
import dto.screen.ScreenResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ScreenService;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetAllScreenController", value = "/getAllScreen", description = "get all screen available in DB by Super Admin")
public class GetAllScreenController extends HttpServlet {

    private final ScreenService screenService = new ScreenService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        ApiResponse apiResponse;
        try {
            List<ScreenResponseDTO> screenResponseDTOS = screenService.getAllScreens();
            apiResponse = new ApiResponse(Message.Success.RECORD_FOUND, screenResponseDTOS);
            response.setStatus(HttpServletResponse.SC_OK);
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
