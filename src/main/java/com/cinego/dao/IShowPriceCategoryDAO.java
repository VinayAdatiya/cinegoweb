package com.cinego.dao;


import com.cinego.common.exception.DBException;
import com.cinego.model.ShowPriceCategory;

import java.sql.Connection;
import java.util.List;

public interface IShowPriceCategoryDAO {
    void addShowPriceCategory(int showId, List<ShowPriceCategory> priceCategories, Connection connection) throws DBException;

    void updateShowPriceCategory(int showId, List<ShowPriceCategory> showPriceCategoryList, int currentUserId, Connection connection) throws DBException;

    List<ShowPriceCategory> getShowPriceCategoriesByShow(int showId) throws DBException;

}
