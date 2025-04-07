package com.cinego.controller.validation;

import com.cinego.common.Message;
import com.cinego.common.exception.ApplicationException;
import com.cinego.dto.show.ShowRequestDTO;
import com.cinego.common.utils.DatabaseUtil;
import com.cinego.common.utils.ValidationUtil;
import com.cinego.dto.show.ShowPriceCategoryDTO;
import java.time.LocalDate;
import java.util.List;

public class ShowValidator {

    public static void validateShow(ShowRequestDTO showRequestDTO) throws ApplicationException {
        if (showRequestDTO == null) {
            throw new ApplicationException(Message.Error.REQUIRED_FIELD_MISSING);
        }

        if (showRequestDTO.getMovieId() <= 0) {
            throw new ApplicationException(Message.Error.REQUIRED_FIELD_MISSING);
        }

        if (!DatabaseUtil.checkRecordExists("movie", "movie_id", showRequestDTO.getMovieId())) {
            throw new ApplicationException(Message.Error.INVALID_ID);
        }

        if (showRequestDTO.getScreenId() <= 0) {
            throw new ApplicationException(Message.Error.REQUIRED_FIELD_MISSING);
        }

        if (!DatabaseUtil.checkRecordExists("screen", "screen_id", showRequestDTO.getScreenId())) {
            throw new ApplicationException(Message.Error.INVALID_ID);
        }

        if (showRequestDTO.getShowDate() == null) {
            throw new ApplicationException(Message.Error.REQUIRED_FIELD_MISSING);
        }

        if (showRequestDTO.getShowDate().isBefore(LocalDate.now())) {
            throw new ApplicationException(Message.Error.NOT_VALID_DATE);
        }

        if (!ValidationUtil.isValidDate(showRequestDTO.getShowDate().toString())) {
            throw new ApplicationException(Message.Error.INVALID_INPUT);
        }

        if (showRequestDTO.getShowTime() == null) {
            throw new ApplicationException(Message.Error.REQUIRED_FIELD_MISSING);
        }

//        if (!ValidationUtil.isValidTime(showRequestDTO.getShowTime().toString())) {
//            throw new ApplicationException(Message.Error.INVALID_INPUT);
//        }

        List<ShowPriceCategoryDTO> showPriceCategoryDTOS = showRequestDTO.getShowPriceCategoryDTOS();
        if (showPriceCategoryDTOS == null || showPriceCategoryDTOS.isEmpty()) {
            throw new ApplicationException(Message.Error.REQUIRED_FIELD_MISSING);
        }
        for (ShowPriceCategoryDTO priceCategoryDTO : showPriceCategoryDTOS) {
            if (priceCategoryDTO.getSeatCategoryId() <= 0) {
                throw new ApplicationException(Message.Error.INVALID_ID);
            }

            if (!DatabaseUtil.checkRecordExists("seat_category", "seat_category_id", priceCategoryDTO.getSeatCategoryId())) {
                throw new ApplicationException(Message.Error.INVALID_ID);
            }

            if (priceCategoryDTO.getPrice() <= 0 || priceCategoryDTO.getPrice() >= 99999999.99) {
                throw new ApplicationException(Message.Error.BASE_PRICE_INVALID);
            }
        }
    }
}
