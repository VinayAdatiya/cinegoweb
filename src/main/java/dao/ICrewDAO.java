package dao;

import common.exception.DBException;
import model.Crew;

import java.util.List;

public interface ICrewDAO {
    int addCrew(Crew crew) throws DBException;
    List<Crew> getAllCrew() throws DBException;
}
