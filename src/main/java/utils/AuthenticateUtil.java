package utils;

import common.Message;
import common.Role;
import common.exception.ApplicationException;
import model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class AuthenticateUtil {
    public static void authorize(HttpServletRequest request, Role requiredRole) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            throw new ApplicationException(Message.Error.SESSION_EXPIRED);
        }
        User user = (User) session.getAttribute("user");
        if (user.getRole().getRoleId() != requiredRole.getRoleId()) {
            throw new ApplicationException(Message.Error.USER_NOT_FOUND);
        }
    }
}
