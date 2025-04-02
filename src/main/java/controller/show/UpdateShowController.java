package controller.show;

import common.AppConstant;
import common.Message;
import common.enums.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import common.utils.AuthenticateUtil;
import common.utils.ObjectMapperUtil;
import controller.validation.ShowValidator;
import dto.show.ShowRequestDTO;
import dto.user.UserResponseDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.ShowService;
import java.io.IOException;

import static common.utils.ResponseUtils.createResponse;

@WebServlet(name = "UpdateShowController", value = "/updateShow", description = "Update Show")
public class UpdateShowController extends HttpServlet {

    private final ShowService showService = new ShowService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            AuthenticateUtil.authorize(request, Role.ROLE_THEATER_ADMIN);
            ShowRequestDTO showRequestDTO = ObjectMapperUtil.toObject(request.getReader(), ShowRequestDTO.class);
            ShowValidator.validateShow(showRequestDTO);
            UserResponseDTO currentUser = (UserResponseDTO) session.getAttribute("user");
            int currentUserId = currentUser.getUserId();
            showService.updateShow(showRequestDTO, currentUserId);
            createResponse(response, Message.Success.RECORD_UPDATED, null, HttpServletResponse.SC_OK);
        } catch (DBException e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NumberFormatException e) {
            throw new ApplicationException(Message.Error.INVALID_ID);
        } catch (ApplicationException e) {
            createResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (IOException e) {
            createResponse(response, Message.Error.INVALID_JSON_REQUEST + e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}