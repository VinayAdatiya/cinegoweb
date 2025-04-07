package com.cinego.controller.validation;

import com.cinego.common.Message;
import com.cinego.common.exception.ApplicationException;
import com.cinego.model.Crew;

public class CrewValidator {

    public static void validateCrew(Crew crew) throws ApplicationException {
        if (crew.getCrewName() == null || crew.getCrewName().trim().isEmpty()) {
            throw new ApplicationException(Message.Error.CREW_NAME_REQUIRED);
        }
    }
}