package com.cinego.dao;

import com.cinego.common.exception.ApplicationException;
import com.cinego.common.exception.DBException;
import com.cinego.model.Theater;

import java.util.List;

public interface ITheaterDAO {
    void addTheater(Theater theater) throws ApplicationException;

    Theater getTheaterById(int theaterId) throws DBException;

    Theater getTheaterByAdminId(int theaterAdminId) throws DBException;

    List<Theater> getAllTheaters() throws DBException;

    void updateTheater(Theater theater) throws DBException;

    void deleteTheater(int theaterId) throws DBException;
}
