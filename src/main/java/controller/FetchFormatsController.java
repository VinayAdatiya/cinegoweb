package controller;

import common.AppConstant;
import common.ObjectMapperUtil;
import model.Format;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.FormatService;

import java.io.IOException;
import java.util.List;

public class FetchFormatsController extends HttpServlet {
    private final FormatService formatService = new FormatService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        List<Format> formats = formatService.getAllFormats();
        response.getWriter().write(ObjectMapperUtil.toString(formats));
    }
}
