package controller.movie;

import common.AppConstant;
import common.Message;
import common.utils.ObjectMapperUtil;
import common.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import jakarta.servlet.annotation.WebServlet;
import model.Crew;
import java.io.IOException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dto.ApiResponse;
import controller.validation.CrewValidator;
import service.CrewService;
import common.utils.AuthenticateUtil;

@WebServlet(name = "AddCrewController" , value = "/addCrew" , description = "Add Crew Member if it is not available in list E.x. SRK or Salman Khan")
public class AddCrewController extends HttpServlet {
    private final CrewService crewService = new CrewService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        ApiResponse apiResponse;
        try {
            AuthenticateUtil.authorize(request, Role.ROLE_SUPER_ADMIN);
            Crew crew = ObjectMapperUtil.toObject(request.getReader(), Crew.class);
            CrewValidator.validateCrew(crew);
            crewService.addCrew(crew);
            apiResponse = new ApiResponse(Message.Success.CREW_ADDED, null);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DBException e) {
            apiResponse = new ApiResponse(Message.Error.INTERNAL_ERROR, null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ApplicationException e) {
            apiResponse = new ApiResponse(e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            apiResponse = new ApiResponse("Server error: " + e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }
}