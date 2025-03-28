package service;

import common.Message;
import common.exception.ApplicationException;
import common.exception.DBException;
import common.utils.DatabaseUtil;
import dao.IShowDAO;
import dao.IShowSeatDAO;
import dao.impl.ShowDAOImpl;
import dao.impl.ShowSeatDAOImpl;
import dto.show.ShowRequestDTO;
import dto.show.ShowResponseDTO;
import mapper.IShowMapper;
import model.Show;
import model.ShowSeat;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

public class ShowService {

    private final IShowDAO showDAO = new ShowDAOImpl();
    private final IShowMapper showMapper = Mappers.getMapper(IShowMapper.class);
    private final IShowSeatDAO showSeatDAO = new ShowSeatDAOImpl();

    public void addShow(ShowRequestDTO showRequestDTO, int currentUserId) throws ApplicationException, DBException {
        Show show = showMapper.toModel(showRequestDTO);
        show.setCreatedBy(currentUserId);
        show.setUpdatedBy(currentUserId);
        showDAO.addShow(show, currentUserId);
    }

    public ShowResponseDTO getShowById(int showId) throws DBException {
        Show show = showDAO.getShowById(showId);
        ShowResponseDTO showResponseDTO = showMapper.toDTO(show);
        List<ShowSeat> showSeats = showSeatDAO.getSeatsByShowId(showId);
        showResponseDTO.setShowSeatList(showSeats);
        return showResponseDTO;
    }

    public List<ShowResponseDTO> getAllShows() throws DBException {
        List<Show> shows = showDAO.getAllShows();
        return shows.stream()
                .map(showMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void updateShow(ShowRequestDTO showRequestDTO, int currentUserId) throws ApplicationException, DBException {
        Show show = showMapper.toModel(showRequestDTO);
        show.setUpdatedBy(currentUserId);
        showDAO.updateShow(show, currentUserId);
    }

    public void deleteShow(int showId) throws ApplicationException, DBException {
        // Assuming showDAO.checkRecordExists checks for show existence
        if (DatabaseUtil.checkRecordExists("show", "show_id", showId)) {
            showDAO.deleteShow(showId);
        } else {
            throw new ApplicationException(Message.Error.INVALID_ID);
        }
    }
}
