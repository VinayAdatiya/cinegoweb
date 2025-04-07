package com.cinego.controller.show;

import com.cinego.common.Message;
import com.cinego.common.enums.Role;
import com.cinego.common.exception.ApplicationException;
import com.cinego.common.utils.ResponseUtils;
import com.cinego.dto.show.ShowRequestDTO;
import com.cinego.dto.user.UserResponseDTO;
import com.cinego.service.ShowService;
import com.cinego.common.AppConstant;
import com.cinego.common.exception.DBException;
import com.cinego.common.utils.AuthenticateUtil;
import com.cinego.common.utils.ObjectMapperUtil;
import com.cinego.controller.validation.ShowValidator;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "AddShowController", value = "/addShow", description = "Add New Show")
public class AddShowController extends HttpServlet {

    private final ShowService showService = new ShowService();

    @Override
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
            showService.addShow(showRequestDTO, currentUserId);
            ResponseUtils.createResponse(response, Message.Success.SHOW_ADDED, null, HttpServletResponse.SC_CREATED);
        } catch (DBException e) {
            ResponseUtils.createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ApplicationException e) {
            ResponseUtils.createResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (IOException e) {
            ResponseUtils.createResponse(response, Message.Error.INVALID_JSON_REQUEST + e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            ResponseUtils.createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}