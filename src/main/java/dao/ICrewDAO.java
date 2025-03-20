package dao;

import common.exception.DBException;
import model.Crew;

import java.sql.Connection;
import java.util.List;

public interface ICrewDAO {
    void addCrew(Crew crew) throws DBException;

    List<Crew> getAllCrew() throws DBException;

    Crew getCrewById(int crewId, Connection connection) throws DBException;
}
