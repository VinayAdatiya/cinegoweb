package com.cinego.controller.validation;

import com.cinego.common.Message;
import com.cinego.common.exception.ApplicationException;
import com.cinego.dto.user.UserResponseDTO;

public class LogoutValidator {
    public static void validateLogout(UserResponseDTO userResponseDTO){
        if(userResponseDTO == null){
            throw new ApplicationException(Message.Error.LOGIN_FIRST);
        }
    }
}
