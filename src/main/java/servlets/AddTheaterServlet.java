package servlets;

import common.AppConstant;
import common.Message;
import common.ObjectMapperUtil;
import common.exception.ApplicationException;
import dao.TheaterDao;
import entity.Theater;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ApiResponse;
import java.io.IOException;
import dao.AddressDao;
import dao.UserDao;
import entity.User;

public class AddTheaterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CONTENT_TYPE_JSON);
        ApiResponse apiResponse;
        try {
            Theater theater = ObjectMapperUtil.toObject(request.getReader(), Theater.class);
            // Fetch Theater Admin
            User theaterAdmin = UserDao.authenticateUser(theater.getTheaterAdmin().getEmail(),theater.getTheaterAdmin().getPassword());
            if (theaterAdmin == null || theaterAdmin.getRole().getRoleId() != 2) {
                throw new ApplicationException("Theater Admin not found");
            }
            theater.setTheaterAdmin(theaterAdmin);
            // Insert address
            int addressId = AddressDao.insertAddress(theater.getTheaterAddress());
            theater.getTheaterAddress().setAddressId(addressId);
            // Add theater
            TheaterDao.addTheater(theater);
            apiResponse = new ApiResponse(Message.Success.THEATER_SUCCESS, null);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (ApplicationException e) {
            apiResponse = new ApiResponse(Message.Error.THEATER_FAILED, null);
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
