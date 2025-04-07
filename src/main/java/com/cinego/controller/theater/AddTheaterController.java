package com.cinego.controller.theater;

import com.cinego.common.AppConstant;
import com.cinego.common.Message;
import com.cinego.common.enums.Role;
import com.cinego.common.exception.ApplicationException;
import com.cinego.common.exception.DBException;
import com.cinego.common.utils.ResponseUtils;
import com.cinego.service.TheaterService;
import com.cinego.common.utils.ObjectMapperUtil;
import com.cinego.dto.theater.TheaterRequestDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.cinego.controller.validation.TheaterValidator;
import com.cinego.common.utils.AuthenticateUtil;

import java.io.IOException;

@WebServlet(name = "AddTheaterController", value = "/addTheater", description = "Setup New Theater TheaterAdmin Credentials are required")
public class AddTheaterController extends HttpServlet {
    private final TheaterService theaterService = new TheaterService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            AuthenticateUtil.authorize(request, Role.ROLE_SUPER_ADMIN);
            TheaterRequestDTO theaterRequestDTO = ObjectMapperUtil.toObject(request.getReader(), TheaterRequestDTO.class);
            TheaterValidator.validateTheater(theaterRequestDTO);
            theaterService.addTheater(theaterRequestDTO);
            ResponseUtils.createResponse(response, Message.Success.THEATER_SUCCESS, null, HttpServletResponse.SC_CREATED);
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
