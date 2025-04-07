package com.cinego.controller.screen;

import com.cinego.common.exception.ApplicationException;
import com.cinego.dto.screen.ScreenResponseDTO;
import com.cinego.common.AppConstant;
import com.cinego.common.Message;
import com.cinego.common.exception.DBException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.cinego.service.ScreenService;
import java.io.IOException;
import java.util.List;

import static com.cinego.common.utils.ResponseUtils.createResponse;

@WebServlet(name = "GetAllScreenController", value = "/getAllScreen", description = "get all screen available in DB by Super Admin")
public class GetAllScreenController extends HttpServlet {

    private final ScreenService screenService = new ScreenService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            List<ScreenResponseDTO> screenResponseDTOS = screenService.getAllScreens();
            createResponse(response, Message.Success.RECORD_FOUND, screenResponseDTOS, HttpServletResponse.SC_OK);
        } catch (DBException e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ApplicationException e) {
            createResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
