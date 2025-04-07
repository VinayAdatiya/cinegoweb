package com.cinego.service;

import com.cinego.dao.impl.CrewDesignationDAOImpl;
import com.cinego.model.CrewDesignation;
import com.cinego.common.exception.DBException;
import com.cinego.dao.ICrewDesignationDAO;

import java.util.List;

public class CrewDesignationService {
    private final ICrewDesignationDAO crewDesignation = new CrewDesignationDAOImpl();

    public List<CrewDesignation> getAllCrewDesignation() throws DBException {
        return crewDesignation.getAllCrewDesignation();
    }
}
