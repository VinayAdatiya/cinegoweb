package controller.movie;

import common.AppConstant;
import common.Message;
import common.utils.ObjectMapperUtil;
import common.exception.DBException;
import dto.ApiResponse;
import jakarta.servlet.annotation.WebServlet;
import model.Language;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.LanguageService;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "FetchLanguagesController" , value = "/getLanguages" , description = "Get All Languages List e.x. English , Hindi etc...")
public class FetchLanguagesController extends HttpServlet {
    private final LanguageService languageService = new LanguageService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ApiResponse apiResponse;
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            List<Language> languages = languageService.getAllLanguages();
            apiResponse = new ApiResponse(Message.Success.LANGUAGES_FOUND, languages);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DBException e) {
            apiResponse = new ApiResponse(Message.Error.INTERNAL_ERROR, null);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            apiResponse = new ApiResponse(Message.Error.INTERNAL_ERROR, null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }
}
