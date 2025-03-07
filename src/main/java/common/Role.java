package common;

import java.util.Arrays;

public enum Role {

    ROLE_SUPER_ADMIN(1),
    ROLE_THEATER_ADMIN(2),
    ROLE_CUSTOMER(3);

    private final int roleId; // Make it final for immutability
    Role(int roleId) {
        this.roleId = roleId; // Use 'this' to refer to the instance variable
    }

    public int getRoleId() {
        return roleId;
    }

    public static Role getRole(int roleId) {
        return Arrays.stream(Role.values())
                .filter(role -> role.getRoleId() == roleId)
                .findFirst()
                .orElse(null);
    }
}