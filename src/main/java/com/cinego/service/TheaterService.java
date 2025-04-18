package com.cinego.service;

import com.cinego.common.Message;
import com.cinego.common.enums.Role;
import com.cinego.common.exception.ApplicationException;
import com.cinego.dao.impl.TheaterDAOImpl;
import com.cinego.dao.impl.UserDAOImpl;
import com.cinego.common.exception.DBException;
import com.cinego.common.utils.DatabaseUtil;
import com.cinego.dao.ITheaterDAO;
import com.cinego.dao.IUserDAO;
import com.cinego.dto.theater.TheaterRequestDTO;
import com.cinego.dto.theater.TheaterResponseDTO;
import com.cinego.mapper.ITheaterMapper;
import com.cinego.model.Theater;
import com.cinego.model.User;
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
        TheaterResponseDTO theaterResponseDTO = theaterMapper.toTheaterResponseDTO(theater);
        if (theaterResponseDTO != null) {
            return theaterResponseDTO;
        } else {
            throw new ApplicationException(Message.Error.NO_RECORD_FOUND);
        }
    }

    public TheaterResponseDTO getTheaterByAdminId(int theaterAdminId) throws DBException {
        Theater theater = theaterDAO.getTheaterByAdminId(theaterAdminId);
        TheaterResponseDTO theaterResponseDTO = theaterMapper.toTheaterResponseDTO(theater);
        if (theaterResponseDTO != null) {
            return theaterResponseDTO;
        } else {
            throw new ApplicationException(Message.Error.NO_RECORD_FOUND);
        }
    }

    public List<TheaterResponseDTO> getAllTheaters() throws DBException {
        List<Theater> theaters = theaterDAO.getAllTheaters();
        return theaters.stream()
                .map(theaterMapper::toTheaterResponseDTO)
                .collect(Collectors.toList());
    }

    public void updateTheater(TheaterRequestDTO theaterRequestDTO, int currentUserId) throws ApplicationException {
        Theater theater = theaterMapper.toTheaterModel(theaterRequestDTO);
        User theaterAdmin = userDAO.authenticateUser(theater.getTheaterAdmin().getEmail(), theater.getTheaterAdmin().getPassword());
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
