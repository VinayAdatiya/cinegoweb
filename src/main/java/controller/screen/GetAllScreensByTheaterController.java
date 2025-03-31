package controller.screen;

import common.AppConstant;
import common.Message;
import common.enums.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import common.utils.AuthenticateUtil;
import common.utils.ObjectMapperUtil;
import dto.ApiResponse;
import dto.screen.ScreenResponseDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ScreenService;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "GetAllScreensByTheaterController", value = "/getAllScreensByTheater", description = "Get All Screens by Theater ID")
public class GetAllScreensByTheaterController extends HttpServlet {

    private final ScreenService screenService = new ScreenService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        ApiResponse apiResponse;
        try {
            AuthenticateUtil.authorizeRole(request, Arrays.asList( Role.ROLE_THEATER_ADMIN, Role.ROLE_SUPER_ADMIN));
            int theaterId = Integer.parseInt(request.getParameter("theaterId"));
            List<ScreenResponseDTO> screens = screenService.getAllScreensByTheater(theaterId);
            if (screens != null && !screens.isEmpty()) {
                apiResponse = new ApiResponse(Message.Success.RECORD_FOUND, screens);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                apiResponse = new ApiResponse(Message.Error.NO_RECORD_FOUND, null);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (DBException e) {
            apiResponse = new ApiResponse(Message.Error.INTERNAL_ERROR, null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ApplicationException e) {
            apiResponse = new ApiResponse(e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (NumberFormatException e) {
            apiResponse = new ApiResponse(Message.Error.INVALID_ID, null);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            apiResponse = new ApiResponse("Server error: " + e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }
}