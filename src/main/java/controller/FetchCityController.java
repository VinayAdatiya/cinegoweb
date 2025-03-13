package controller;

import common.AppConstant;
import common.ObjectMapperUtil;
import model.City;
import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CityService;

public class FetchCityController extends HttpServlet {
    private final CityService cityService = new CityService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        List<City> cities = cityService.getAllCities();
        response.getWriter().write(ObjectMapperUtil.toString(cities));
    }
}
