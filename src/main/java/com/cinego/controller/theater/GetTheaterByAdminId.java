package com.cinego.controller.theater;

import com.cinego.common.AppConstant;
import com.cinego.common.Message;
import com.cinego.common.exception.ApplicationException;
import com.cinego.common.exception.DBException;
import com.cinego.common.utils.ResponseUtils;
import com.cinego.dto.theater.TheaterResponseDTO;
import com.cinego.dto.user.UserResponseDTO;
import com.cinego.service.TheaterService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "GetTheaterByAdminId", value = "/getTheaterByAdminId", description = "Fetch Particular Theater By Theater Admin Id")
public class GetTheaterByAdminId extends HttpServlet {
    private final TheaterService theaterService = new TheaterService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            HttpSession session = request.getSession(false);
            UserResponseDTO currentUser = (UserResponseDTO) session.getAttribute("user");
            int theaterAdminId = currentUser.getUserId();
            TheaterResponseDTO theaterResponseDTO = theaterService.getTheaterByAdminId(theaterAdminId);
            ResponseUtils.createResponse(response, Message.Success.RECORD_FOUND, theaterResponseDTO, HttpServletResponse.SC_OK);
        } catch (DBException e) {
            ResponseUtils.createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NumberFormatException e) {
            throw new ApplicationException(Message.Error.INVALID_ID);
        } catch (ApplicationException e) {
            ResponseUtils.createResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            ResponseUtils.createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}