package controller;

import common.AppConstant;
import common.Message;
import common.utils.ObjectMapperUtil;
import common.exception.ApplicationException;
import controller.validation.LogoutValidator;
import dto.user.UserResponseDTO;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dto.ApiResponse;
import java.io.IOException;

public class LogoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        ApiResponse apiResponse;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                UserResponseDTO userResponseDTO = (UserResponseDTO) session.getAttribute("user");
                LogoutValidator.validateLogout(userResponseDTO);
                session.invalidate();
                apiResponse = new ApiResponse(Message.Success.LOGOUT_SUCCESS, null);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                throw new ApplicationException(Message.Error.SESSION_EXPIRED);
            }
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
