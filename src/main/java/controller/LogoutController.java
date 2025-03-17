package controller;

import common.AppConstant;
import common.Message;
import common.ObjectMapperUtil;
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
        HttpSession session = request.getSession(false);
        if (session != null) {
            if (session.getAttribute("user") == null ){
                apiResponse = new ApiResponse(Message.Error.LOGIN_FIRST, null);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            session.invalidate();
            apiResponse = new ApiResponse(Message.Success.LOGOUT_SUCCESS, null);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            apiResponse = new ApiResponse(Message.Error.INTERNAL_ERROR, null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }
}
