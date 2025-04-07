package com.cinego.controller;

import com.cinego.common.AppConstant;
import com.cinego.common.Message;
import com.cinego.common.exception.ApplicationException;
import com.cinego.common.exception.DBException;
import com.cinego.common.utils.ResponseUtils;
import com.cinego.controller.validation.LoginValidator;
import com.cinego.dto.user.UserResponseDTO;
import com.cinego.model.User;
import com.cinego.service.UserService;
import com.cinego.common.utils.ObjectMapperUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginController", value = "/login", description = "Common Login API for all Users")
public class LoginController extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            User userRequest = ObjectMapperUtil.toObject(request.getReader(), User.class);
            LoginValidator.validateLogin(userRequest);
            UserResponseDTO userResponseDTO = userService.authenticateUser(userRequest.getEmail(), userRequest.getPassword());
            HttpSession session = request.getSession();
            session.setAttribute("user", userResponseDTO);
            ResponseUtils.createResponse(response, Message.Success.LOGIN_SUCCESS, userResponseDTO.getRole().getRoleId(), HttpServletResponse.SC_OK);
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
