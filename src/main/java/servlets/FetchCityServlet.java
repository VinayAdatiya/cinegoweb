package servlets;

import common.AppConstant;
import common.ObjectMapperUtil;
import dao.CityDao;
import entity.City;
import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FetchCityServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        List<City> cities = CityDao.getAllCities();
        response.getWriter().write(ObjectMapperUtil.toString(cities));
    }
}
