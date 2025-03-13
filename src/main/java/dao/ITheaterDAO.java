package dao;

import model.Theater;

import java.sql.SQLException;

public interface ITheaterDAO {
    void addTheater(Theater theater) throws SQLException;
}
