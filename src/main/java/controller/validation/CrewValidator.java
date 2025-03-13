package controller.validation;

import common.Message;
import common.exception.ApplicationException;
import model.Crew;

public class CrewValidator {

    public static void validateCrew(Crew crew) throws ApplicationException {
        if (crew.getCrewName() == null || crew.getCrewName().trim().isEmpty()) {
            throw new ApplicationException(Message.Error.CREW_NAME_REQUIRED);
        }
    }
}