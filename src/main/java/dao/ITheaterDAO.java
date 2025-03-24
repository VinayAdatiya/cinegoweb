package dao;

import common.exception.ApplicationException;
import common.exception.DBException;
import model.Theater;

import java.util.List;

public interface ITheaterDAO {
    void addTheater(Theater theater) throws ApplicationException;

    Theater getTheaterById(int theaterId) throws DBException;

    List<Theater> getAllTheaters() throws DBException;

    void updateTheater(Theater theater) throws DBException;

    void deleteTheater(int theaterId) throws DBException;
}
