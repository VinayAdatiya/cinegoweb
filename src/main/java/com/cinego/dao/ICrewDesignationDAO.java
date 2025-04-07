package com.cinego.dao;

import com.cinego.common.exception.DBException;
import com.cinego.model.CrewDesignation;

import java.sql.Connection;
import java.util.List;

public interface ICrewDesignationDAO {
    List<CrewDesignation> getAllCrewDesignation() throws DBException;

    CrewDesignation getDesignationById(int designationId, Connection connection) throws DBException;
}
