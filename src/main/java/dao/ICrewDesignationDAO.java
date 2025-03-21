package dao;

import common.exception.DBException;
import model.CrewDesignation;

import java.sql.Connection;
import java.util.List;

public interface ICrewDesignationDAO {
    List<CrewDesignation> getAllCrewDesignation() throws DBException;

    CrewDesignation getDesignationById(int designationId, Connection connection) throws DBException;
}
