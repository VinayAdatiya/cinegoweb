package com.cinego.service;

import com.cinego.common.Message;
import com.cinego.common.exception.ApplicationException;
import com.cinego.dao.IShowSeatDAO;
import com.cinego.dao.impl.ShowSeatDAOImpl;
import com.cinego.dto.show.ShowRequestDTO;
import com.cinego.dto.show.ShowResponseDTO;
import com.cinego.dto.show.ShowSeatResponseDTO;
import com.cinego.mapper.IShowMapper;
import com.cinego.mapper.IShowSeatMapper;
import com.cinego.model.Show;
import com.cinego.model.ShowPriceCategory;
import com.cinego.model.ShowSeat;
import com.cinego.common.exception.DBException;
import com.cinego.common.utils.DatabaseUtil;
import com.cinego.dao.IShowDAO;
import com.cinego.dao.impl.ShowDAOImpl;
import com.cinego.dto.show.ShowPriceCategoryDTO;
import com.cinego.mapper.IShowPriceCategoryMapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

public class ShowService {

    private final IShowDAO showDAO = new ShowDAOImpl();
    private final IShowMapper showMapper = Mappers.getMapper(IShowMapper.class);
    private final IShowPriceCategoryMapper showPriceCategoryMapper = Mappers.getMapper(IShowPriceCategoryMapper.class);
    private final IShowSeatMapper showSeatMapper = Mappers.getMapper(IShowSeatMapper.class);
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
        List<ShowSeatResponseDTO> showSeatResponseDTOS = showSeats.stream()
                .map(showSeatMapper::toDTO)
                .collect(Collectors.toList());
        showResponseDTO.setShowSeatList(showSeatResponseDTOS);
        return showResponseDTO;
    }

    public List<ShowResponseDTO> getShowByTheaterId(int theaterId) throws ApplicationException {
        List<Show> shows = showDAO.getShowByTheaterId(theaterId);
        if (shows.isEmpty()) {
            throw new ApplicationException(Message.Error.NO_RECORD_FOUND);
        }
        return shows.stream()
                .map(showMapper::toDTO)
                .collect(Collectors.toList());
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
