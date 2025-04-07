package com.cinego.service;

import com.cinego.model.Crew;
import com.cinego.common.exception.DBException;
import com.cinego.dao.impl.CrewDAOImpl;
import com.cinego.dao.ICrewDAO;

import java.util.List;

public class CrewService {
    private final ICrewDAO crewDAO = new CrewDAOImpl();
    public void addCrew(Crew crew) throws DBException{
        crewDAO.addCrew(crew);
    }
    public List<Crew> getAllCrew() throws DBException{
        return crewDAO.getAllCrew();
    }
}
