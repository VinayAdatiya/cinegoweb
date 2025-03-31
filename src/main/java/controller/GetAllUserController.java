package controller;

import common.AppConstant;
import common.Message;
import common.enums.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import common.utils.AuthenticateUtil;
import common.utils.ObjectMapperUtil;
import dto.ApiResponse;
import dto.user.UserResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetAllUserController", value = "/getAllUsers", description = "Get All User")
public class GetAllUserController extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        ApiResponse apiResponse;
        try {
            AuthenticateUtil.authorize(request, Role.ROLE_SUPER_ADMIN);
            List<UserResponseDTO> userResponseDTOs = userService.getAllUsers();
            apiResponse = new ApiResponse(Message.Success.RECORD_FOUND, userResponseDTOs);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DBException e) {
            apiResponse = new ApiResponse(Message.Error.INTERNAL_ERROR, null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
