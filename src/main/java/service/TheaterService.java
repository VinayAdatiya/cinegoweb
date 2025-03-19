package service;

import common.Role;
import common.exception.ApplicationException;
import dao.ITheaterDAO;
import dao.impl.TheaterDAOImpl;
import model.Theater;

public class TheaterService {
    private final ITheaterDAO theaterDAO = new TheaterDAOImpl();

    public void addTheater(Theater theater) throws ApplicationException {
        theater.setCreatedBy(Role.ROLE_SUPER_ADMIN.getRoleId());
        theaterDAO.addTheater(theater);
    }
}
