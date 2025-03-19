package service;

import common.exception.DBException;
import dao.impl.CrewDAOImpl;
import dao.ICrewDAO;
import model.Crew;

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
