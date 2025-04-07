package com.cinego.dao;

import com.cinego.model.ScreenType;
import com.cinego.common.exception.DBException;

public interface IScreenTypeDAO {
    ScreenType getScreenTypeById(int screenTypeId) throws DBException;
}
