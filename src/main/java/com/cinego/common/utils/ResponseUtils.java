package com.cinego.common.utils;

import com.cinego.dto.ApiResponseDTO;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResponseUtils {
    public static void createResponse(HttpServletResponse response, String message, Object data, int statusCode) throws IOException {
        response.setStatus(statusCode);
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO(message, data);
        response.getWriter().write(ObjectMapperUtil.toString(apiResponseDTO));
    }
}