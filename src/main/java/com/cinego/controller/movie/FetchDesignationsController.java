package com.cinego.controller.movie;

import com.cinego.common.Message;
import com.cinego.common.utils.ResponseUtils;
import com.cinego.model.CrewDesignation;
import com.cinego.common.AppConstant;
import com.cinego.common.exception.DBException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.cinego.service.CrewDesignationService;

import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "FetchDesignationsController",
        value = "/getCrewDesignations",
        description = "Fetch Crew Designations List E.x. Producer , Sound Team etc. ")
public class FetchDesignationsController extends HttpServlet {
    private final CrewDesignationService crewDesignationService = new CrewDesignationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            List<CrewDesignation> designationList = crewDesignationService.getAllCrewDesignation();
            ResponseUtils.createResponse(response, Message.Success.DESIGNATIONS_FOUND, designationList, HttpServletResponse.SC_OK);
        } catch (DBException e) {
            ResponseUtils.createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            ResponseUtils.createResponse(response, Message.Error.SERVER_ERROR + e.getMessage(), null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
