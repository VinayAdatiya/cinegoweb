package controller.address;

import common.AppConstant;
import common.Message;
import common.exception.DBException;
import jakarta.servlet.annotation.WebServlet;
import model.City;
import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CityService;

import static common.utils.ResponseUtils.createResponse;

@WebServlet(name = "FetchCityController", value = "/fetchCities", description = "Fetch all Cities List")
public class FetchCityController extends HttpServlet {
    private final CityService cityService = new CityService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            List<City> cities = cityService.getAllCities();
            createResponse(response, Message.Success.CITIES_FOUND, cities, HttpServletResponse.SC_OK);
        } catch (DBException e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            createResponse(response, Message.Error.SERVER_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
