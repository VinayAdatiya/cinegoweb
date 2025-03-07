package servlets.validation;

import common.Message;
import common.ValidationUtil;
import common.exception.ApplicationException;
import entity.User;

public class LoginValidator {
    public static void validateLogin(User user) throws ApplicationException {
        if (user.getPassword() == null || user.getPassword().isEmpty() || user.getEmail() == null) {
            throw new ApplicationException(Message.Error.REQUIRED_FIELD_MISSING);
        }

        if (!ValidationUtil.isValidEmail(user.getEmail())) {
            throw new ApplicationException(Message.Error.EMAIL_INVALID);
        }
    }
}
