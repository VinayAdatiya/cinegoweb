package controller;

import common.AppConstant;
import common.ObjectMapperUtil;
import dao.GenreDao;
import entity.Genre;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FetchGenresController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        List<Genre> genres = GenreDao.getAllGenres();
        response.getWriter().write(ObjectMapperUtil.toString(genres));
    }
}
