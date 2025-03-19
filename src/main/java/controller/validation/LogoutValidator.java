package controller.validation;

import common.Message;
import common.exception.ApplicationException;
import dto.user.UserResponseDTO;

public class LogoutValidator {
    public static void validateLogout(UserResponseDTO userResponseDTO){
        if(userResponseDTO == null){
            throw new ApplicationException(Message.Error.LOGIN_FIRST);
        }
    }
}
