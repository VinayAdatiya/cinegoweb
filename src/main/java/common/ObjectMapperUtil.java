package common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.ApiResponse;

import java.io.BufferedReader;
import java.io.IOException;

public class ObjectMapperUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T toObject(BufferedReader reader, Class<T> targetType) throws IOException {
        return objectMapper.readValue(reader, targetType);
    }

    public static String toString(ApiResponse apiResponse) throws JsonProcessingException {
        return objectMapper.writeValueAsString(apiResponse);
    }
}
