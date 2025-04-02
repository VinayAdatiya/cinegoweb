package controller.movie;

import common.AppConstant;
import common.Message;
import common.exception.DBException;
import jakarta.servlet.annotation.WebServlet;
import model.Language;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.LanguageService;
import java.io.IOException;
import java.util.List;

import static common.utils.ResponseUtils.createResponse;

@WebServlet(name = "FetchLanguagesController", value = "/getLanguages", description = "Get All Languages List e.x. English , Hindi etc...")
public class FetchLanguagesController extends HttpServlet {
    private final LanguageService languageService = new LanguageService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            List<Language> languages = languageService.getAllLanguages();
            createResponse(response, Message.Success.LANGUAGES_FOUND, languages, HttpServletResponse.SC_OK);
        } catch (DBException e) {
            createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            createResponse(response, Message.Error.SERVER_ERROR + e.getMessage(), null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
