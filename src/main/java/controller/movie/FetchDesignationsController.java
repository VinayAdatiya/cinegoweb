package controller.movie;

import common.AppConstant;
import common.Message;
import common.exception.DBException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CrewDesignation;
import service.CrewDesignationService;

import java.io.IOException;
import java.util.List;

import static common.utils.ResponseUtils.createResponse;

@WebServlet(
        name = "FetchDesignationsController",
        value = "/getCrewDesignations",
        description = "Fetch Crew Designations List E.x. Producer , Sound Team etc. ")
public class FetchDesignationsController extends HttpServlet {
    private final CrewDesignationService crewDesignationService = new CrewDesignationService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            List<CrewDesignation> designationList = crewDesignationService.getAllCrewDesignation();
            createResponse(response, Message.Success.DESIGNATIONS_FOUND, designationList, HttpServletResponse.SC_OK);
        } catch (DBException e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            createResponse(response, Message.Error.SERVER_ERROR + e.getMessage(), null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
