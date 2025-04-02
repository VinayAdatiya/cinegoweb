package controller.theater;

import common.AppConstant;
import common.Message;
import common.utils.ObjectMapperUtil;
import common.enums.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import dto.theater.TheaterRequestDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import controller.validation.TheaterValidator;
import service.TheaterService;
import common.utils.AuthenticateUtil;

import java.io.IOException;

import static common.utils.ResponseUtils.createResponse;

@WebServlet(name = "AddTheaterController", value = "/addTheater", description = "Setup New Theater TheaterAdmin Credentials are required")
public class AddTheaterController extends HttpServlet {
    private final TheaterService theaterService = new TheaterService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            AuthenticateUtil.authorize(request, Role.ROLE_SUPER_ADMIN);
            TheaterRequestDTO theaterRequestDTO = ObjectMapperUtil.toObject(request.getReader(), TheaterRequestDTO.class);
            TheaterValidator.validateTheater(theaterRequestDTO);
            theaterService.addTheater(theaterRequestDTO);
            createResponse(response, Message.Success.THEATER_SUCCESS, null, HttpServletResponse.SC_CREATED);
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
