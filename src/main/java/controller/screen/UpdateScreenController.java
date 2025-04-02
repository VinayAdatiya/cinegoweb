package controller.screen;

import common.AppConstant;
import common.Message;
import common.enums.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import common.utils.AuthenticateUtil;
import common.utils.ObjectMapperUtil;
import controller.validation.ScreenValidator;
import dto.screen.ScreenRequestDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ScreenService;

import java.io.IOException;

import static common.utils.ResponseUtils.createResponse;

@WebServlet(name = "UpdateScreenController", value = "/updateScreen", description = "Update Screen Info by Theater Admin")
public class UpdateScreenController extends HttpServlet {

    private final ScreenService screenService = new ScreenService();

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            AuthenticateUtil.authorize(request, Role.ROLE_THEATER_ADMIN);
            ScreenRequestDTO screenRequestDTO = ObjectMapperUtil.toObject(request.getReader(), ScreenRequestDTO.class);
            ScreenValidator.validateScreen(screenRequestDTO);
            screenService.updateScreen(screenRequestDTO);
            createResponse(response, Message.Success.RECORD_UPDATED, null, HttpServletResponse.SC_OK);
        } catch (DBException e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ApplicationException e) {
            createResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (IOException e) {
            createResponse(response, Message.Error.INVALID_JSON_REQUEST + e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
