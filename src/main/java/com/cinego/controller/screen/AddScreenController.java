package com.cinego.controller.screen;

import com.cinego.common.enums.Role;
import com.cinego.dto.screen.ScreenRequestDTO;
import com.cinego.dto.user.UserResponseDTO;
import com.cinego.common.AppConstant;
import com.cinego.common.Message;
import com.cinego.common.exception.ApplicationException;
import com.cinego.common.exception.DBException;
import com.cinego.common.utils.AuthenticateUtil;
import com.cinego.common.utils.ObjectMapperUtil;
import com.cinego.controller.validation.ScreenValidator;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.cinego.service.ScreenService;
import java.io.IOException;

import static com.cinego.common.utils.ResponseUtils.createResponse;

@WebServlet(name = "AddScreenController", value = "/addScreen", description = "Add Screen to Theater By Theater Admin")
public class AddScreenController extends HttpServlet {
    private final ScreenService screenService = new ScreenService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            AuthenticateUtil.authorize(request, Role.ROLE_THEATER_ADMIN);
            ScreenRequestDTO screenRequestDTO = ObjectMapperUtil.toObject(request.getReader(), ScreenRequestDTO.class);
            ScreenValidator.validateScreen(screenRequestDTO);
            UserResponseDTO currentUser = (UserResponseDTO) session.getAttribute("user");
            int currentUserId = currentUser.getUserId();
            screenService.addScreen(screenRequestDTO, currentUserId);
            createResponse(response, Message.Success.SCREEN_ADDED, null, HttpServletResponse.SC_CREATED);
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
