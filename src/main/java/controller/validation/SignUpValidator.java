package controller.validation;

import common.Message;
import common.utils.ValidationUtil;
import common.exception.ApplicationException;
import dto.user.UserSignUpDTO;
import service.UserService;

public class SignUpValidator {

    public static void validateSignup(UserSignUpDTO userSignUpDTO , UserService userService) throws ApplicationException {

        if (userSignUpDTO.getUserName() == null || userSignUpDTO.getUserName().trim().isEmpty() ||
                userSignUpDTO.getPassword() == null || userSignUpDTO.getPassword().trim().isEmpty() ||
                userSignUpDTO.getEmail() == null || userSignUpDTO.getEmail().trim().isEmpty() ||
                userSignUpDTO.getPhoneNumber() == null || userSignUpDTO.getPhoneNumber().trim().isEmpty() ||
                userSignUpDTO.getAddress() == null || userSignUpDTO.getAddress().getAddressLine1() == null || userSignUpDTO.getAddress().getAddressLine1().trim().isEmpty() ||
                userSignUpDTO.getAddress().getCity() == null || userSignUpDTO.getAddress().getCity().getCityId() == 0) {
            throw new ApplicationException(Message.Error.REQUIRED_FIELD_MISSING);
        }

        if (userSignUpDTO.getAddress() != null && userSignUpDTO.getAddress().getPincode() <= 0) {
            throw new ApplicationException(Message.Error.PINCODE_INVALID);
        }

        if (userService.isEmailExist(userSignUpDTO.getEmail())) {
            throw new ApplicationException(Message.Error.EMAIL_EXISTS);
        }

        if (!ValidationUtil.isValidLength(userSignUpDTO.getEmail(), 50)) {
            throw new ApplicationException(Message.Error.EMAIL_TOO_LONG);
        }

        if (!ValidationUtil.isValidLength(userSignUpDTO.getPassword(), 50)) {
            throw new ApplicationException(Message.Error.PASSWORD_TOO_LONG);
        }

        if (!ValidationUtil.isValidEmail(userSignUpDTO.getEmail())) {
            throw new ApplicationException(Message.Error.EMAIL_INVALID);
        }

        if (!ValidationUtil.isValidPassword(userSignUpDTO.getPassword())) {
            throw new ApplicationException(Message.Error.PASSWORD_WEAK);
        }

        if (userService.usernameExists(userSignUpDTO.getUserName())) {
            throw new ApplicationException(Message.Error.USERNAME_EXISTS);
        }

        if (!ValidationUtil.isValidLength(userSignUpDTO.getUserName(), 20)) {
            throw new ApplicationException(Message.Error.USERNAME_TOO_LONG);
        }

        if (!ValidationUtil.isValidLength(userSignUpDTO.getFirstName(), 30)) {
            throw new ApplicationException(Message.Error.FIRST_NAME_TOO_LONG);
        }

        if (!ValidationUtil.isValidLength(userSignUpDTO.getLastName(), 30)) {
            throw new ApplicationException(Message.Error.LAST_NAME_TOO_LONG);
        }

        if (!ValidationUtil.isNumeric(userSignUpDTO.getPhoneNumber())) {
            throw new ApplicationException(Message.Error.PHONE_INVALID);
        }

        if (!ValidationUtil.isValidLength(userSignUpDTO.getPhoneNumber(), 15)) {
            throw new ApplicationException(Message.Error.PHONE_TOO_LONG);
        }

        if (!ValidationUtil.isValidLength(userSignUpDTO.getAddress().getAddressLine1(), 100)) {
            throw new ApplicationException(Message.Error.ADDRESS_TOO_LONG);
        }

        if (!ValidationUtil.isValidLength(userSignUpDTO.getAddress().getAddressLine2(), 100)) {
            throw new ApplicationException(Message.Error.ADDRESS_TOO_LONG);
        }

        if (!ValidationUtil.isNumeric(Integer.toString(userSignUpDTO.getAddress().getPincode()))) {
            throw new ApplicationException(Message.Error.PIN_CODE_INVALID);
        }
    }

}
