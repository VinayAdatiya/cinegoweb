package com.cinego.controller.screen;

import com.cinego.common.AppConstant;
import com.cinego.common.Message;
import com.cinego.common.exception.ApplicationException;
import com.cinego.common.exception.DBException;
import com.cinego.common.utils.ResponseUtils;
import com.cinego.model.ScreenType;
import com.cinego.service.ScreenService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static com.cinego.common.utils.ResponseUtils.createResponse;

@WebServlet(name = "GetAllScreenTypes", value = "/getAllScreenTypes", description = "Get All Screen Types")
public class GetAllScreenTypes extends HttpServlet {
    private final ScreenService screenService = new ScreenService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            List<ScreenType> screenTypeList = screenService.getAllScreenType();
            ResponseUtils.createResponse(response, Message.Success.RECORD_FOUND, screenTypeList, HttpServletResponse.SC_OK);
        } catch (DBException e) {
            ResponseUtils.createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (ApplicationException e) {
            createResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            ResponseUtils.createResponse(response, Message.Error.SERVER_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
