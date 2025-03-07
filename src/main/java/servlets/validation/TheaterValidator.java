package servlets.validation;

import common.Message;
import common.exception.ApplicationException;
import entity.Theater;

public class TheaterValidator {
    public static void validateTheater(Theater theater) throws ApplicationException {
        if(theater.getTheaterName() == null || theater.getTheaterAddress() == null || theater.getTheaterAdmin() == null){
            throw new ApplicationException(Message.Error.REQUIRED_FIELD_MISSING);
        }
    }
}
