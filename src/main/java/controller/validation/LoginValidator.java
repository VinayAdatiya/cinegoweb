package controller.validation;

import common.Message;
import common.utils.ValidationUtil;
import common.exception.ApplicationException;
import model.User;

public class LoginValidator {
    public static void validateLogin(User user) throws ApplicationException {
        if (user.getPassword() == null || user.getPassword().trim().isEmpty() || user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new ApplicationException(Message.Error.REQUIRED_FIELD_MISSING);
        }

        if (!ValidationUtil.isValidEmail(user.getEmail())) {
            throw new ApplicationException(Message.Error.EMAIL_INVALID);
        }
    }
}
