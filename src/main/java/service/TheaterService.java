package service;

import dao.ITheaterDAO;
import dao.TheaterDAOImpl;
import model.Theater;

import java.sql.SQLException;

public class TheaterService {
    private final ITheaterDAO theaterDAO = new TheaterDAOImpl();

    public void addTheater(Theater theater) throws SQLException {
        theaterDAO.addTheater(theater);
    }
}
