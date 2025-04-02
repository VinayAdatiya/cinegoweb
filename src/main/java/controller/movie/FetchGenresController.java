package controller.movie;

import common.AppConstant;
import common.Message;
import common.exception.DBException;
import jakarta.servlet.annotation.WebServlet;
import model.Genre;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.GenreService;
import java.io.IOException;
import java.util.List;

import static common.utils.ResponseUtils.createResponse;

@WebServlet(name = "FetchGenresController", value = "/getGenres", description = "Get All Genres List e.x. SciFi , Comedy etc...")
public class FetchGenresController extends HttpServlet {
    private final GenreService genreService = new GenreService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            List<Genre> genres = genreService.getAllGenres();
            createResponse(response, Message.Success.GENRES_FOUND, genres, HttpServletResponse.SC_OK);
        } catch (DBException e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            createResponse(response, Message.Error.SERVER_ERROR + e.getMessage(), null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
