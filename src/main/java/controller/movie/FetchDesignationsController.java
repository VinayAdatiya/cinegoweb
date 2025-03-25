package controller.movie;

import common.AppConstant;
import common.Message;
import common.utils.ObjectMapperUtil;
import common.exception.DBException;
import dto.ApiResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CrewDesignation;
import service.CrewDesignationService;

import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "FetchDesignationsController",
        value = "/getCrewDesignations",
        description = "Fetch Crew Designations List E.x. Producer , Sound Team etc. ")
public class FetchDesignationsController extends HttpServlet {
    private final CrewDesignationService crewDesignationService = new CrewDesignationService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        ApiResponse apiResponse;
        try {
            List<CrewDesignation> designationList = crewDesignationService.getAllCrewDesignation();
            apiResponse = new ApiResponse(Message.Success.DESIGNATIONS_FOUND, designationList);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DBException e) {
            apiResponse = new ApiResponse(Message.Error.INTERNAL_ERROR, null);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            apiResponse = new ApiResponse(Message.Error.INTERNAL_ERROR, null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }
}
