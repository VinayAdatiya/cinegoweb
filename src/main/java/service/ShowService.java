package service;

import common.Message;
import common.exception.ApplicationException;
import common.exception.DBException;
import common.utils.DatabaseUtil;
import dao.IShowDAO;
import dao.IShowSeatDAO;
import dao.impl.ShowDAOImpl;
import dao.impl.ShowSeatDAOImpl;
import dto.ApiResponseDTO;
import dto.show.ShowPriceCategoryDTO;
import dto.show.ShowRequestDTO;
import dto.show.ShowResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import mapper.IShowMapper;
import mapper.IShowPriceCategoryMapper;
import model.Show;
import model.ShowPriceCategory;
import model.ShowSeat;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

public class ShowService {

    private final IShowDAO showDAO = new ShowDAOImpl();
    private final IShowMapper showMapper = Mappers.getMapper(IShowMapper.class);
    private final IShowPriceCategoryMapper showPriceCategoryMapper = Mappers.getMapper(IShowPriceCategoryMapper.class);
    private final IShowSeatDAO showSeatDAO = new ShowSeatDAOImpl();

    public void addShow(ShowRequestDTO showRequestDTO, int currentUserId) throws ApplicationException, DBException {
        Show show = showMapper.toModel(showRequestDTO);
        List<ShowPriceCategory> showPriceCategories = showRequestDTO.getShowPriceCategoryDTOS().stream().map(showPriceCategoryMapper::toModel).collect(Collectors.toList());
        show.setShowPriceCategoryList(showPriceCategories);
        show.setCreatedBy(currentUserId);
        show.setUpdatedBy(currentUserId);
        showDAO.addShow(show, currentUserId);
    }

    public ShowResponseDTO getShowById(int showId) throws DBException {
        Show show = showDAO.getShowById(showId);
        List<ShowPriceCategoryDTO> showPriceCategoryDTOS = show.getShowPriceCategoryList().stream().map(showPriceCategoryMapper::toDTO).collect(Collectors.toList());
        ShowResponseDTO showResponseDTO = showMapper.toDTO(show);
        showResponseDTO.setShowPriceCategoryList(showPriceCategoryDTOS);
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
        List<ShowPriceCategory> showPriceCategories = showRequestDTO.getShowPriceCategoryDTOS().stream().map(showPriceCategoryMapper::toModel).collect(Collectors.toList());
        show.setShowPriceCategoryList(showPriceCategories);
        show.setUpdatedBy(currentUserId);
        showDAO.updateShow(show, currentUserId);
    }

    public void deleteShow(int showId) throws ApplicationException {
        if (DatabaseUtil.checkRecordExists("shows", "show_id", showId)) {
            showDAO.deleteShow(showId);
        } else {
            throw new ApplicationException(Message.Error.INVALID_ID);
        }
    }
}
