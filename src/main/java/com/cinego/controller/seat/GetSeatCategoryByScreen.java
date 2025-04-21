package com.cinego.controller.seat;

import com.cinego.common.AppConstant;
import com.cinego.common.Message;
import com.cinego.common.exception.ApplicationException;
import com.cinego.common.exception.DBException;
import com.cinego.common.utils.ResponseUtils;
import com.cinego.model.SeatCategory;
import com.cinego.service.SeatService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static com.cinego.common.utils.ResponseUtils.createResponse;

@WebServlet(name = "GetSeatCategoryByScreen", value = "/getSeatCategoryByScreen", description = "Fetch all Seat Categories List By Screen Id")
public class GetSeatCategoryByScreen extends HttpServlet {
    private final SeatService seatService = new SeatService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(AppConstant.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(AppConstant.CHAR_ENCODE_UTF8);
        try {
            int screenId = Integer.parseInt(request.getParameter("screenId"));
            List<SeatCategory> seatCategories = seatService.getSeatCategoryByScreen(screenId);
            ResponseUtils.createResponse(response, Message.Success.RECORD_FOUND, seatCategories, HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            throw new ApplicationException(Message.Error.INVALID_ID);
        } catch (DBException e) {
            ResponseUtils.createResponse(response, Message.Error.INTERNAL_ERROR, null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (ApplicationException e) {
            createResponse(response, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            ResponseUtils.createResponse(response, Message.Error.SERVER_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
