package dao;


import common.exception.DBException;
import model.ShowPriceCategory;

import java.sql.Connection;
import java.util.List;

public interface IShowPriceCategoryDAO {
    void addShowPriceCategory(int showId, List<ShowPriceCategory> priceCategories, int currentUserId, Connection connection) throws DBException;

    void updateShowPriceCategory(int showId, List<ShowPriceCategory> showPriceCategoryList, int currentUserId, Connection connection) throws DBException;

    List<ShowPriceCategory> getShowPriceCategoriesByShow(int showId) throws DBException;

}
