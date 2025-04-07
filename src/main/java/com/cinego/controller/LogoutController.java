package com.cinego.controller;

import com.cinego.common.AppConstant;
import com.cinego.common.Message;
import com.cinego.common.exception.ApplicationException;
import com.cinego.common.utils.ResponseUtils;
import com.cinego.controller.validation.LogoutValidator;
import com.cinego.dto.user.UserResponseDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginController", value = "/logout", description = "Logout & Invalidate Session")
public class LogoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                UserResponseDTO userResponseDTO = (UserResponseDTO) session.getAttribute("user");
                LogoutValidator.validateLogout(userResponseDTO);
                session.invalidate();
                ResponseUtils.createResponse(response, Message.Success.LOGOUT_SUCCESS, null, HttpServletResponse.SC_OK);
            } else {
                throw new ApplicationException(Message.Error.SESSION_EXPIRED);
            }
        } catch (ApplicationException e) {
            ResponseUtils.createResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            ResponseUtils.createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
