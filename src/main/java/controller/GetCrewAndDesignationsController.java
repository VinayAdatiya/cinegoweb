package controller;

import common.AppConstant;
import common.Message;
import common.ObjectMapperUtil;
import common.exception.DBException;
import model.Crew;
import model.CrewDesignation;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dto.ApiResponse;
import service.CrewDesignationService;
import service.CrewService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetCrewAndDesignationsController extends HttpServlet {
    private final CrewDesignationService crewDesignationService = new CrewDesignationService();
    private final CrewService crewService = new CrewService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        ApiResponse apiResponse;
        try {
            List<Crew> crewList = crewService.getAllCrew();
            List<CrewDesignation> designationList = crewDesignationService.getAllCrewDesignation();
            HashMap<String,List> hm = new HashMap<>();
            hm.put("crewList",crewList);
            hm.put("designationList",designationList);
            apiResponse = new ApiResponse(Message.Success.DESIGNATIONS_FETCHED, hm);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DBException e) {
            apiResponse = new ApiResponse("Database Error: " + e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            apiResponse = new ApiResponse("Server error: " + e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }
}