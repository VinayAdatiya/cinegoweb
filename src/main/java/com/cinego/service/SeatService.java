package com.cinego.service;

import com.cinego.common.Message;
import com.cinego.common.exception.ApplicationException;
import com.cinego.dao.ISeatCategoryDAO;
import com.cinego.dao.impl.SeatCategoryDAOImpl;
import com.cinego.model.SeatCategory;

import java.util.List;

public class SeatService {
    private final ISeatCategoryDAO seatCategoryDAO = new SeatCategoryDAOImpl();

    public List<SeatCategory> getAllSeatCategories() {
        List<SeatCategory> seatCategories = seatCategoryDAO.getAllSeatCategories();
        if (seatCategories == null || seatCategories.isEmpty()) {
            throw new ApplicationException(Message.Error.NO_RECORD_FOUND);
        }
        return seatCategories;
    }

    public List<SeatCategory> getSeatCategoryByScreen(int screenId) {
        List<SeatCategory> seatCategories = seatCategoryDAO.getSeatCategoriesByScreen(screenId);
        if (seatCategories == null || seatCategories.isEmpty()) {
            throw new ApplicationException(Message.Error.NO_RECORD_FOUND);
        }
        return seatCategories;
    }
}
