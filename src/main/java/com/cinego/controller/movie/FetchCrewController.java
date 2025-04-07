package com.cinego.controller.movie;

import com.cinego.common.Message;
import com.cinego.common.utils.ResponseUtils;
import com.cinego.model.Crew;
import com.cinego.service.CrewService;
import com.cinego.common.AppConstant;
import com.cinego.common.exception.DBException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "FetchCrewController", value = "/getCrewMembers", description = "Get All Crew Members List")
public class FetchCrewController extends HttpServlet {
    private final CrewService crewService = new CrewService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            List<Crew> crewList = crewService.getAllCrew();
            ResponseUtils.createResponse(response, Message.Success.CREW_FOUND, crewList, HttpServletResponse.SC_OK);
        } catch (DBException e) {
            ResponseUtils.createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            ResponseUtils.createResponse(response, Message.Error.SERVER_ERROR + e.getMessage(), null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
