package dao;

import common.exception.DBException;
import model.ScreenType;

import java.sql.Connection;

public interface IScreenTypeDAO {
    ScreenType getScreenTypeById(int screenTypeId) throws DBException;
}
