package com.cinego.common.utils;

import com.cinego.common.Message;
import com.cinego.common.enums.Role;
import com.cinego.common.exception.ApplicationException;
import com.cinego.dto.user.UserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class AuthenticateUtil {
    public static void authorize(HttpServletRequest request, Role requiredRole) throws ApplicationException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            throw new ApplicationException(Message.Error.SESSION_EXPIRED);
        }
        UserResponseDTO user = (UserResponseDTO) session.getAttribute("user");
        if (user.getRole().getRoleId() != requiredRole.getRoleId()) {
            throw new ApplicationException(Message.Error.USER_NOT_FOUND);
        }
    }

    public static void authorizeRole(HttpServletRequest request, List<Role> roles) throws ApplicationException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            throw new ApplicationException(Message.Error.SESSION_EXPIRED);
        }
        UserResponseDTO user = (UserResponseDTO) session.getAttribute("user");
        if (roles.stream().noneMatch(role -> role == user.getRole())) {
            throw new ApplicationException(Message.Error.USER_NOT_FOUND);
        }
    }
}
