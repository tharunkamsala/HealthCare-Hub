package com.demo.storage.views;



import com.demo.exceptions.AppointmentAlreadyExistsException;
import com.demo.exceptions.PatientAlreadyExistsException;
import com.demo.storage.RoleFactory;
import com.demo.storage.interfaces.UserDao;
import com.demo.storage.models.Pateint;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class UserService {
    private final UserDao userDao;

    // Constructor to initialize with a UserDao obtained from RoleFactory
    public UserService(Connection connection) {
        this.userDao = (UserDao) RoleFactory.getRoleImplementation(RoleFactory.USER_ROLE,connection);
    }

    // Method for registering a new patient
    public void registerPatient(Pateint patient) throws SQLException, PatientAlreadyExistsException {
        userDao.registerPatient(patient);
    }

    // Method for booking an appointment
    public boolean bookAppointment(int doctorId, int patientId, Date appointmentDate) throws SQLException, AppointmentAlreadyExistsException {
        return userDao.bookAppointment(doctorId, patientId, appointmentDate);
    }
}
