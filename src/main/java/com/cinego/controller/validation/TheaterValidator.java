package com.cinego.controller.validation;

import com.cinego.common.Message;
import com.cinego.common.exception.ApplicationException;
import com.cinego.common.utils.ValidationUtil;
import com.cinego.dto.theater.TheaterRequestDTO;

public class TheaterValidator {
    public static void validateTheater(TheaterRequestDTO theaterRequestDTO) throws ApplicationException {
        if (theaterRequestDTO.getTheaterName() == null || theaterRequestDTO.getTheaterName().trim().isEmpty() || theaterRequestDTO.getTheaterAddress() == null || theaterRequestDTO.getTheaterAdmin() == null) {
            throw new ApplicationException(Message.Error.REQUIRED_FIELD_MISSING);
        }
        if (!ValidationUtil.isValidLength(theaterRequestDTO.getTheaterName(), 30)) {
            throw new ApplicationException(Message.Error.THEATER_NAME_TOO_LONG);
        }
    }
}
