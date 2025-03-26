package dao;

import common.exception.DBException;
import model.ScreenType;

public interface IScreenTypeDAO {
    ScreenType getScreenTypeById(int screenTypeId) throws DBException;
}
