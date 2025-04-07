package com.cinego.dao;

import com.cinego.model.SeatCategory;
import com.cinego.common.exception.DBException;

import java.util.List;

public interface ISeatCategoryDAO {
    List<SeatCategory> getAllSeatCategories() throws DBException;
    List<SeatCategory> getSeatCategoriesByScreen(int screenId) throws DBException;
    SeatCategory getSeatCategoryById(int seatCategoryId) throws DBException;
}
