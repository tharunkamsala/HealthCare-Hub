package com.demo.storage;


import java.sql.Connection;

public class RoleFactory {

    // Constants to represent roles
    public static final String ADMIN_ROLE = "admin";
    public static final String DOCTOR_ROLE = "doctor";
    public static final String USER_ROLE = "user";

    // Factory method to get the correct implementation based on the role
    public static Object getRoleImplementation(String role, Connection connection) {
        switch (role.toLowerCase()) {
            case ADMIN_ROLE:
                return new AdminDaoImpl(connection);  // Returns AdminDao implementation
            case DOCTOR_ROLE:
                return new DoctorDaoImpl();           // Returns DoctorDao implementation
            case USER_ROLE:
                return new UserDaoImpl();             // Returns UserDao implementation
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
}
