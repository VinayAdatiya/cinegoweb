package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.Message;
import common.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import common.utils.DatabaseUtil;
import dao.IScreenDAO;
import dao.ISeatDAO;
import dao.ITheaterDAO;
import dao.impl.ScreenDAOImpl;
import dao.impl.SeatDAOImpl;
import dao.impl.TheaterDAOImpl;
import dto.screen.ScreenRequestDTO;
import dto.screen.ScreenResponseDTO;
import mapper.IScreenMapper;
import model.Layout;
import model.Screen;
import model.Seat;
import model.SeatCategory;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ScreenService {

    private final IScreenDAO screenDAO = new ScreenDAOImpl();
    private final ITheaterDAO theaterDAO = new TheaterDAOImpl();
    private final IScreenMapper screenMapper = Mappers.getMapper(IScreenMapper.class);

    public void addScreen(ScreenRequestDTO screenRequestDTO, int currentUserId) throws ApplicationException {
        // Validate Owner of Theater is Same as Current User
        int theaterId = screenRequestDTO.getTheater().getTheaterId();
        if (theaterDAO.getTheaterById(theaterId).getTheaterAdmin().getUserId() == currentUserId) {
            Screen screen = screenMapper.toScreenModel(screenRequestDTO);
            screen.setCreatedBy(currentUserId);
            screen.setUpdatedBy(currentUserId);
            screenDAO.addScreen(screen);
        } else {
            throw new ApplicationException(Message.Error.INVALID_ADMIN_PRIVILEGED);
        }
    }

    public ScreenResponseDTO getScreenById(int screenId) throws DBException {
        Screen screen = screenDAO.getScreenById(screenId);
        if (screen == null) {
            throw new ApplicationException(Message.Error.NO_RECORD_FOUND);
        }
        return screenMapper.toScreenResponseDTO(screen);
    }

    public List<ScreenResponseDTO> getAllScreens() throws DBException {
        List<Screen> screens = screenDAO.getAllScreens();
        if (screens.isEmpty()) {
            throw new ApplicationException(Message.Error.NO_RECORD_FOUND);
        }
        return screens.stream()
                .map(screenMapper::toScreenResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ScreenResponseDTO> getAllScreensByTheater(int theaterId) throws DBException {
        if (DatabaseUtil.checkRecordExists("theater", "theater_id", theaterId)) {
            List<Screen> screens = screenDAO.getAllScreensByTheater(theaterId);
            return screens.stream()
                    .map(screenMapper::toScreenResponseDTO)
                    .collect(Collectors.toList());
        } else {
            throw new ApplicationException(Message.Error.INVALID_ID);
        }
    }


    public void updateScreen(ScreenRequestDTO screenRequestDTO) throws ApplicationException {
        Screen screen = screenMapper.toScreenModel(screenRequestDTO);
        screen.setUpdatedBy(Role.ROLE_THEATER_ADMIN.getRoleId());
        screenDAO.updateScreen(screen);
    }

    public void deleteScreen(int screenId) throws ApplicationException {
        if (DatabaseUtil.checkRecordExists("screen", "screen_id", screenId)) {
            screenDAO.deleteScreen(screenId);
        } else {
            throw new ApplicationException(Message.Error.INVALID_ID);
        }
    }
}