package com.demo.storage.views;

import com.demo.exceptions.AppointmentNotFoundException;
import com.demo.exceptions.DoctorNotFoundException;
import com.demo.exceptions.ScheduleExistsException;
import com.demo.exceptions.ScheduleNotFoundException;
import com.demo.storage.RoleFactory;
import com.demo.storage.interfaces.DoctorDao;
import com.demo.storage.models.Appointment;
import com.demo.storage.models.Medication;
import com.demo.storage.models.Schedule;
import com.demo.storage.models.Tests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DoctorService {
    private final DoctorDao doctorDao;
    private final Connection connection;

    // Constructor to initialize with a DoctorDao obtained from RoleFactory and a database connection
    public DoctorService(Connection connection) {
        this.connection = connection;
        this.doctorDao = (DoctorDao) RoleFactory.getRoleImplementation(RoleFactory.DOCTOR_ROLE, connection);
    }

    // Adding a schedule with exception handling
    public void addSchedule(Schedule schedule) {
        try {
            System.out.println("Schedule added successfully!!!");
            doctorDao.addSchedule(schedule);
        } catch (ScheduleExistsException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Updating a schedule with exception handling
    public void updateSchedule(Schedule schedule) {
        try {
            System.out.println("Schedule updated successfully!!!");
            doctorDao.updateSchedule(schedule);
        } catch (ScheduleNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieving appointments by doctor ID with exception handling
    public List<Appointment> getAppointmentsByDoctorId(int doctorId) {
        try {
            return doctorDao.getAppointmentsByDoctorId(doctorId);
        } catch (DoctorNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Canceling an appointment with exception handling
    public void cancelAppointment(int appointmentId) {
        try {
            // Check for associated medications
            if (hasAssociatedMedications(appointmentId)) {
                System.out.println("Cannot cancel appointment: Medications are associated with it.");
                return;
            }

            // Check for associated tests
            if (hasAssociatedTests(appointmentId)) {
                System.out.println("Cannot cancel appointment: Tests are associated with it.");
                return;
            }

            // Proceed with cancellation if no medications or tests are found
            doctorDao.cancelAppointment(appointmentId);
            System.out.println("Appointment cancelled successfully.");

        } catch (AppointmentNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean hasAssociatedMedications(int appointmentId) throws SQLException {
        String query = "SELECT COUNT(*) FROM medications WHERE appointment_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, appointmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean hasAssociatedTests(int appointmentId) throws SQLException {
        String query = "SELECT COUNT(*) FROM tests WHERE appointment_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, appointmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Adding medications to an appointment with exception handling
    public void addMedicationsToAppointment(int appointmentId, List<Medication> medications) {
        try {
            System.out.println("Medications Added !!!!");
            doctorDao.addMedicationsToAppointment(appointmentId, medications);
        } catch (AppointmentNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Adding tests to an appointment with exception handling
    public void addTestsToAppointment(int appointmentId, List<Tests> tests) {
        try {
            System.out.println("Tests added !!!!");
            doctorDao.addTestsToAppointment(appointmentId, tests);
        } catch (AppointmentNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
