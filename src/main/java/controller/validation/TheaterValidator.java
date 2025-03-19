package controller.validation;

import common.Message;
import common.utils.ValidationUtil;
import common.exception.ApplicationException;
import model.Theater;

public class TheaterValidator {
    public static void validateTheater(Theater theater) throws ApplicationException {
        if(theater.getTheaterName() == null || theater.getTheaterName().trim().isEmpty() || theater.getTheaterAddress() == null || theater.getTheaterAdmin() == null){
            throw new ApplicationException(Message.Error.REQUIRED_FIELD_MISSING);
        }
        if(ValidationUtil.isValidLength(theater.getTheaterName(),30)){
            throw new ApplicationException(Message.Error.THEATER_NAME_TOO_LONG);
        }
    }
}
