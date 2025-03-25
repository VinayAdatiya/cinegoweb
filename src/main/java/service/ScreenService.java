package service;

import common.Message;
import common.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import common.utils.DatabaseUtil;
import dao.IScreenDAO;
import dao.impl.ScreenDAOImpl;
import dto.screen.ScreenRequestDTO;
import dto.screen.ScreenResponseDTO;
import mapper.IScreenMapper;
import model.Screen;
import org.mapstruct.factory.Mappers;
import java.util.List;
import java.util.stream.Collectors;

public class ScreenService {

    private final IScreenDAO screenDAO = new ScreenDAOImpl();
    private final IScreenMapper screenMapper = Mappers.getMapper(IScreenMapper.class);

    public void addScreen(ScreenRequestDTO screenRequestDTO) throws ApplicationException {
        Screen screen = screenMapper.toScreenModel(screenRequestDTO);
        screen.setCreatedBy(Role.ROLE_THEATER_ADMIN.getRoleId());
        screenDAO.addScreen(screen);
    }

    public ScreenResponseDTO getScreenById(int screenId) throws DBException {
        Screen screen = screenDAO.getScreenById(screenId);
        return screenMapper.toScreenResponseDTO(screen);

    }

    public List<ScreenResponseDTO> getAllScreens() throws DBException {
        List<Screen> screens = screenDAO.getAllScreens();
        return screens.stream()
                .map(screenMapper::toScreenResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ScreenResponseDTO> getAllScreensByTheater(int theaterId) throws DBException{
        if (DatabaseUtil.checkRecordExists("theater", "theater_id", theaterId)) {
//            screenDAO.getAllScreens(theaterId);
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