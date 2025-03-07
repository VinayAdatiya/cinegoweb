package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.AppConstant;
import dao.CityDao;
import entity.City;
import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FetchCityServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);

        System.out.println("Fetching cities....");
        List<City> cities = CityDao.getAllCities();
//        cities.stream().forEach((c) -> System.out.println(c.getCityId()+" "+c.getCityName()+" "+c.getState()));

        ObjectMapper objectMapper = new ObjectMapper();

        response.getWriter().write(objectMapper.writeValueAsString(cities));
    }
}
