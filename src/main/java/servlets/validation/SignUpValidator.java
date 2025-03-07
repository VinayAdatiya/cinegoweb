package servlets.validation;

import common.Message;
import common.ValidationUtil;
import common.exception.ApplicationException;
import dao.UserDao;
import entity.User;

public class SignUpValidator {

    public static void validateSignup(User user) throws ApplicationException {

        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null || user.getPhoneNumber() == null
                || user.getAddress().getAddressLine1() == null || user.getAddress().getPincode() == 0
                || user.getAddress().getCity().getCityId() == 0) {
            throw new ApplicationException(Message.Error.REQUIRED_FIELD_MISSING);
        }

        if (UserDao.emailExists(user.getEmail())) {
            throw new ApplicationException(Message.Error.EMAIL_EXISTS);
        }

        if (!ValidationUtil.isValidLength(user.getEmail(), 50)) {
            throw new ApplicationException(Message.Error.EMAIL_TOO_LONG);
        }

        if (!ValidationUtil.isValidLength(user.getPassword(), 50)) {
            throw new ApplicationException(Message.Error.PASSWORD_TOO_LONG);
        }

        if (!ValidationUtil.isValidEmail(user.getEmail())) {
            throw new ApplicationException(Message.Error.EMAIL_INVALID);
        }

        if (!ValidationUtil.isValidPassword(user.getPassword())) {
            throw new ApplicationException(Message.Error.PASSWORD_WEAK);
        }

        if (UserDao.usernameExists(user.getUsername())) {
            throw new ApplicationException(Message.Error.USERNAME_EXISTS);
        }

        if (!ValidationUtil.isValidLength(user.getUsername(), 20)) {
            throw new ApplicationException(Message.Error.USERNAME_TOO_LONG);
        }

        if (!ValidationUtil.isValidLength(user.getFirstName(), 30)) {
            throw new ApplicationException(Message.Error.FIRST_NAME_TOO_LONG);
        }

        if (!ValidationUtil.isValidLength(user.getLastName(), 30)) {
            throw new ApplicationException(Message.Error.LAST_NAME_TOO_LONG);
        }

        if (!ValidationUtil.isNumeric(user.getPhoneNumber())) {
            throw new ApplicationException(Message.Error.PHONE_INVALID);
        }

        if (!ValidationUtil.isValidLength(user.getPhoneNumber(), 15)) {
            throw new ApplicationException(Message.Error.PHONE_TOO_LONG);
        }

        if (!ValidationUtil.isValidLength(user.getAddress().getAddressLine1(), 100)) {
            throw new ApplicationException(Message.Error.ADDRESS_TOO_LONG);
        }

        if (!ValidationUtil.isValidLength(user.getAddress().getAddressLine2(), 100)) {
            throw new ApplicationException(Message.Error.ADDRESS_TOO_LONG);
        }

        if (!ValidationUtil.isNumeric(Integer.toString(user.getAddress().getPincode()))) {
            throw new ApplicationException(Message.Error.PIN_CODE_INVALID);
        }
    }

}
