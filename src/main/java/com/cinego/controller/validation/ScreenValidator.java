package com.cinego.controller.validation;

import com.cinego.common.Message;
import com.cinego.common.exception.ApplicationException;
import com.cinego.dto.screen.ScreenRequestDTO;
import com.cinego.model.Layout;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.cinego.common.utils.DatabaseUtil;
import com.cinego.common.utils.ObjectMapperUtil;
import com.cinego.common.utils.ValidationUtil;

public class ScreenValidator {

    public static void validateScreen(ScreenRequestDTO screenRequestDTO) throws ApplicationException {
        if (screenRequestDTO.getScreenId() != 0) {
            if (!DatabaseUtil.checkRecordExists("screen", "screen_id", screenRequestDTO.getScreenId())) {
                throw new ApplicationException(Message.Error.INVALID_ID);
            }
        }

        if (screenRequestDTO.getScreenTitle() == null || screenRequestDTO.getScreenTitle().trim().isEmpty()) {
            throw new ApplicationException(Message.Error.SCREEN_TITLE_REQUIRED);
        }

        if (!ValidationUtil.isValidLength(screenRequestDTO.getScreenTitle(), 10)) {
            throw new ApplicationException(Message.Error.SCREEN_TITLE_TOO_LONG);
        }

        if (screenRequestDTO.getTotalSeats() <= 0) {
            throw new ApplicationException(Message.Error.SCREEN_TOTAL_SEATS_INVALID);
        }

        if (screenRequestDTO.getScreenType().getScreenTypeId() <= 0) {
            if (!DatabaseUtil.checkRecordExists("screen_types", "screen_type_id", screenRequestDTO.getScreenType().getScreenTypeId())) {
                throw new ApplicationException(Message.Error.INVALID_SCREEN_TYPE_ID);
            }
        }

        if (screenRequestDTO.getLayout() == null || screenRequestDTO.getLayout().trim().isEmpty()) {
            throw new ApplicationException(Message.Error.SCREEN_LAYOUT_REQUIRED);
        }

        if (screenRequestDTO.getTheater().getTheaterId() <= 0) {
            throw new ApplicationException(Message.Error.SCREEN_THEATER_ID_REQUIRED);
        } else {
            if (!DatabaseUtil.checkRecordExists("theater", "theater_id", screenRequestDTO.getTheater().getTheaterId())) {
                throw new ApplicationException(Message.Error.INVALID_THEATER_ID);
            }
        }

        try{
            String layoutJson = screenRequestDTO.getLayout();
            Layout layout = ObjectMapperUtil.toObject(layoutJson, Layout.class);
            if(layout == null){
                throw new ApplicationException(Message.Error.REQUIRED_FIELD_MISSING);
            }
        } catch (JsonProcessingException e) {
            throw new ApplicationException(Message.Error.INTERNAL_ERROR);
        }
    }
}