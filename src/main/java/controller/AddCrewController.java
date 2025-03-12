package controller;

import common.AppConstant;
import common.Message;
import common.ObjectMapperUtil;
import common.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import dao.CrewDao;
import entity.Crew;
import entity.User;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ApiResponse;
import controller.validation.CrewValidator;
import utils.AuthenticateUtil;

public class AddCrewController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        AuthenticateUtil.authorize(request,Role.ROLE_SUPER_ADMIN);
        ApiResponse apiResponse;
        try {
            Crew crew = ObjectMapperUtil.toObject(request.getReader(), Crew.class);
            CrewValidator.validateCrew(crew);
            try {
                int crewId = CrewDao.addCrew(crew);
                apiResponse = new ApiResponse(Message.Success.CREW_ADDED, crewId);
                response.setStatus(HttpServletResponse.SC_CREATED);
            } catch (DBException e) {
                apiResponse = new ApiResponse("Database Error: " + e.getMessage(), null);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

        } catch (ApplicationException e) {
            apiResponse = new ApiResponse(e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (IOException e) {
            apiResponse = new ApiResponse("Invalid JSON request: " + e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            apiResponse = new ApiResponse("Server error: " + e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }
}