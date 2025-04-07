package com.cinego.controller.movie;

import com.cinego.controller.validation.CrewValidator;
import com.cinego.common.AppConstant;
import com.cinego.common.Message;
import com.cinego.common.utils.ObjectMapperUtil;
import com.cinego.common.enums.Role;
import com.cinego.common.exception.ApplicationException;
import com.cinego.common.exception.DBException;
import jakarta.servlet.annotation.WebServlet;
import com.cinego.model.Crew;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.cinego.service.CrewService;
import com.cinego.common.utils.AuthenticateUtil;

import static com.cinego.common.utils.ResponseUtils.createResponse;

@WebServlet(name = "AddCrewController", value = "/addCrew", description = "Add Crew Member if it is not available in list E.x. SRK or Salman Khan")
public class AddCrewController extends HttpServlet {
    private final CrewService crewService = new CrewService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            AuthenticateUtil.authorize(request, Role.ROLE_SUPER_ADMIN);
            Crew crew = ObjectMapperUtil.toObject(request.getReader(), Crew.class);
            CrewValidator.validateCrew(crew);
            crewService.addCrew(crew);
            createResponse(response, Message.Success.CREW_ADDED, null, HttpServletResponse.SC_CREATED);
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