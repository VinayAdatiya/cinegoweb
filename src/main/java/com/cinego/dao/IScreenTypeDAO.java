package com.cinego.dao;

import com.cinego.model.ScreenType;
import com.cinego.common.exception.DBException;

import java.util.List;

public interface IScreenTypeDAO {
    List<ScreenType> getAllScreenType() throws DBException;

    ScreenType getScreenTypeById(int screenTypeId) throws DBException;
}
