package controller;

import common.AppConstant;
import common.ObjectMapperUtil;
import model.Language;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.LanguageService;

import java.io.IOException;
import java.util.List;

public class FetchLanguagesController extends HttpServlet {
    private final LanguageService languageService = new LanguageService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        List<Language> languages = languageService.getAllLanguages();
        response.getWriter().write(ObjectMapperUtil.toString(languages));
    }
}
