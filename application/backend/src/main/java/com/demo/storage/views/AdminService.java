package com.demo.storage.views;



import com.demo.exceptions.DoctorExistsException;
import com.demo.exceptions.DoctorNotFoundException;
import com.demo.storage.RoleFactory;
import com.demo.storage.interfaces.AdminDao;
import com.demo.storage.models.Appointment;
import com.demo.storage.models.Doctor;
import com.demo.storage.models.Pateint;
import com.demo.storage.models.Schedule;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AdminService {
    private final AdminDao adminDao;

    // Constructor to initialize with an AdminDao obtained from RoleFactory
    public AdminService(Connection connection) {
        this.adminDao = (AdminDao) RoleFactory.getRoleImplementation(RoleFactory.ADMIN_ROLE, connection);
    }

    // Doctor management
    public void addDoctor(Doctor doctor) throws DoctorExistsException, SQLException {
        adminDao.addDoctor(doctor);
    }

    public void updateDoctor(Doctor doctor) throws DoctorNotFoundException, SQLException {
        adminDao.updateDoctor(doctor);
    }

    public void cancelAllAppointments(int doctorId) throws DoctorNotFoundException, SQLException {
        adminDao.cancelAllAppointments(doctorId);
    }

    // Schedule management
    public void addSchedule(Schedule schedule) throws SQLException {
        adminDao.addSchedule(schedule);
    }

    public void updateSchedule(Schedule schedule) throws SQLException {
        adminDao.updateSchedule(schedule);
    }

    public void removeSchedule(int doctorId) throws SQLException {
        adminDao.removeSchedule(doctorId);
    }

    // Reporting
    public List<Doctor> getAllDoctors() throws SQLException {
        return adminDao.getAllDoctors();
    }

    public List<Appointment> getAllAppointments() throws SQLException {
        return adminDao.getAllAppointments();
    }

    public List<Pateint> getAllPatients() throws SQLException {
        return adminDao.getAllPatients();
    }
}
