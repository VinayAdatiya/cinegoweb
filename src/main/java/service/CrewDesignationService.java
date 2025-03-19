package service;

import common.exception.DBException;
import dao.impl.CrewDesignationDAOImpl;
import dao.ICrewDesignation;
import model.CrewDesignation;
import java.util.List;

public class CrewDesignationService {
    private final ICrewDesignation crewDesignation = new CrewDesignationDAOImpl();

    public List<CrewDesignation> getAllCrewDesignation() throws DBException {
        return crewDesignation.getAllCrewDesignation();
    }
}
