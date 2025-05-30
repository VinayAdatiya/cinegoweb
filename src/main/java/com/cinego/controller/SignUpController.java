package com.cinego.controller;

import com.cinego.common.Message;
import com.cinego.common.enums.Role;
import com.cinego.common.exception.ApplicationException;
import com.cinego.controller.validation.SignUpValidator;
import com.cinego.dto.user.UserSignUpDTO;
import com.cinego.common.AppConstant;
import com.cinego.common.utils.ObjectMapperUtil;
import com.cinego.common.exception.DBException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.cinego.service.UserService;

import java.io.IOException;

import static com.cinego.common.utils.ResponseUtils.createResponse;

@WebServlet(name = "SignUpController", value = "/signup", description = "Normal User Signup")
public class SignUpController extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            UserSignUpDTO userSignUpDTO = ObjectMapperUtil.toObject(request.getReader(), UserSignUpDTO.class);
            SignUpValidator.validateSignup(userSignUpDTO, userService);
            userSignUpDTO.setRole(Role.ROLE_CUSTOMER);
            userService.registerUser(userSignUpDTO);
            createResponse(response, Message.Success.SIGNUP_SUCCESS, null, HttpServletResponse.SC_CREATED);
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
