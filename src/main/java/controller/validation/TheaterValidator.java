package controller.validation;

import common.Message;
import common.utils.ValidationUtil;
import common.exception.ApplicationException;
import dto.theater.TheaterRequestDTO;

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
