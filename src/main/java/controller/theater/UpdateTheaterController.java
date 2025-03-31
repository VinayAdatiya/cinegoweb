package controller.theater;

import common.AppConstant;
import common.Message;
import common.enums.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import common.utils.AuthenticateUtil;
import common.utils.ObjectMapperUtil;
import controller.validation.TheaterValidator;
import dto.ApiResponse;
import dto.theater.TheaterRequestDTO;
import dto.user.UserResponseDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.TheaterService;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(name = "UpdateTheaterController" , value = "/updateTheater" , description = "Update Theater Info (It Does Not Update Theater Admin Info)")
public class UpdateTheaterController extends HttpServlet {

    private final TheaterService theaterService = new TheaterService();

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        ApiResponse apiResponse;
        try {
            AuthenticateUtil.authorizeRole(request, Arrays.asList(Role.ROLE_SUPER_ADMIN,Role.ROLE_THEATER_ADMIN));
            TheaterRequestDTO theaterRequestDTO = ObjectMapperUtil.toObject(request.getReader(), TheaterRequestDTO.class);
            TheaterValidator.validateTheater(theaterRequestDTO);
            HttpSession session = request.getSession(false);
            UserResponseDTO currentUser = (UserResponseDTO) session.getAttribute("user");
            int currentUserId = currentUser.getUserId();
            theaterService.updateTheater(theaterRequestDTO,currentUserId);
            apiResponse = new ApiResponse(Message.Success.RECORD_UPDATED, null);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DBException e) {
            apiResponse = new ApiResponse(Message.Error.INTERNAL_ERROR, null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ApplicationException e) {
            apiResponse = new ApiResponse(e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (IOException e) {
            apiResponse = new ApiResponse("Invalid JSON request: " + e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            apiResponse = new ApiResponse("Server error: " + e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }
}
