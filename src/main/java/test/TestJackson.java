package test;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.State;

public class TestJackson {
    public static void main(String[] args) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(new State("GJ", "Gujarat"));
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}