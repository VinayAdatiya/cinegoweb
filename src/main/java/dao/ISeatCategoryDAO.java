package dao;

import common.exception.DBException;
import model.SeatCategory;

import java.util.List;

public interface ISeatCategoryDAO {
    List<SeatCategory> getAllSeatCategories() throws DBException;
    List<SeatCategory> getSeatCategoriesByScreen(int screenId) throws DBException;
    SeatCategory getSeatCategoryById(int seatCategoryId) throws DBException;
}
