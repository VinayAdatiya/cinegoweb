package dao;

import common.exception.ApplicationException;
import common.exception.DBException;
import model.Theater;

import java.sql.SQLException;

public interface ITheaterDAO {
    void addTheater(Theater theater) throws ApplicationException;
}
