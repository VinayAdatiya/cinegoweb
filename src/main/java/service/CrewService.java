package service;

import common.exception.DBException;
import dao.CrewDAOImpl;
import dao.ICrewDAO;
import model.Crew;

import java.util.List;

public class CrewService {
    private final ICrewDAO crewDAO = new CrewDAOImpl();
    public int addCrew(Crew crew) throws DBException{
        return crewDAO.addCrew(crew);
    }
    public List<Crew> getAllCrew() throws DBException{
        return crewDAO.getAllCrew();
    }
}
