package service;

import common.Message;
import common.enums.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import common.utils.DatabaseUtil;
import dao.ITheaterDAO;
import dao.IUserDAO;
import dao.impl.TheaterDAOImpl;
import dao.impl.UserDAOImpl;
import dto.theater.TheaterRequestDTO;
import dto.theater.TheaterResponseDTO;
import mapper.ITheaterMapper;
import model.Theater;
import model.User;
import org.mapstruct.factory.Mappers;
import java.util.List;
import java.util.stream.Collectors;

public class TheaterService {
    private final ITheaterMapper theaterMapper = Mappers.getMapper(ITheaterMapper.class);
    private final ITheaterDAO theaterDAO = new TheaterDAOImpl();
    private final IUserDAO userDAO = new UserDAOImpl();

    public void addTheater(TheaterRequestDTO theaterRequestDTO) throws ApplicationException {
        Theater theater = theaterMapper.toTheaterModel(theaterRequestDTO);
        theater.setCreatedBy(Role.ROLE_SUPER_ADMIN.getRoleId());
        theater.setUpdatedBy(Role.ROLE_SUPER_ADMIN.getRoleId());
        theaterDAO.addTheater(theater);
    }

    public TheaterResponseDTO getTheaterById(int theaterId) throws DBException {
        Theater theater = theaterDAO.getTheaterById(theaterId);
        return theaterMapper.toTheaterResponseDTO(theater);
    }

    public List<TheaterResponseDTO> getAllTheaters() throws DBException {
        List<Theater> theaters = theaterDAO.getAllTheaters();
        return theaters.stream()
                .map(theaterMapper::toTheaterResponseDTO)
                .collect(Collectors.toList());
    }

    public void updateTheater(TheaterRequestDTO theaterRequestDTO,int currentUserId) throws ApplicationException {
        Theater theater = theaterMapper.toTheaterModel(theaterRequestDTO);
        User theaterAdmin = userDAO.authenticateUser(theater.getTheaterAdmin().getEmail(),theater.getTheaterAdmin().getPassword());
        theater.setUpdatedBy(currentUserId);
        theater.setTheaterAdmin(theaterAdmin);
        theaterDAO.updateTheater(theater);
    }

    public void deleteTheater(int theaterId) throws ApplicationException {
        if (DatabaseUtil.checkRecordExists("theater", "theater_id", theaterId)) {
            theaterDAO.deleteTheater(theaterId);
        } else {
            throw new ApplicationException(Message.Error.INVALID_ID);
        }
    }
}
