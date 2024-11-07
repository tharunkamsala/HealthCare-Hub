package com.demo.storage;

import com.demo.exceptions.*;
import com.demo.storage.models.*;
import com.demo.storage.interfaces.*;

import java.sql.*;
import java.util.Date;

public class UserDaoImpl implements UserDao {
    private final String url = "jdbc:mysql://localhost:3306/hms";
    private final String username = "root";
    private final String password = "root";

    // Method to establish a database connection
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public void registerPatient(Pateint patient) throws SQLException, PatientAlreadyExistsException {
        String checkSql = "SELECT * FROM patients WHERE contact_info = ?";
        String insertSql = "INSERT INTO patients (name, dob, contact_info) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, patient.getContactInfo());
            ResultSet rs = checkStmt.executeQuery();

            // Check if patient already exists
            if (rs.next()) {
                throw new PatientAlreadyExistsException("Patient with this contact info already exists.");
            }

            // Register the patient
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, patient.getName());
                insertStmt.setDate(2, new java.sql.Date(patient.getDao().getTime()));
                insertStmt.setString(3, patient.getContactInfo());
                insertStmt.executeUpdate();
            }
        }
    }

    @Override
    public boolean bookAppointment(int doctorId, int patientId, Date appointmentDate) throws AppointmentAlreadyExistsException, SQLException {
        String checkAppointmentQuery = "SELECT * FROM appointments WHERE doctor_id = ? AND patient_id = ? AND appointment_date = ?";
        String checkScheduleQuery = "SELECT * FROM schedules WHERE doctor_id = ? AND start_date <= ? AND end_date >= ? AND is_available = TRUE";
        String insertAppointmentQuery = "INSERT INTO appointments (doctor_id, patient_id, appointment_date, status) VALUES (?, ?, ?, 'Scheduled')";

        try (Connection conn = getConnection();
             PreparedStatement checkAppointmentStmt = conn.prepareStatement(checkAppointmentQuery);
             PreparedStatement checkScheduleStmt = conn.prepareStatement(checkScheduleQuery)) {

            // Check if an appointment already exists for this patient and doctor at the specified date
            checkAppointmentStmt.setInt(1, doctorId);
            checkAppointmentStmt.setInt(2, patientId);
            checkAppointmentStmt.setDate(3, new java.sql.Date(appointmentDate.getTime()));
            ResultSet appointmentRs = checkAppointmentStmt.executeQuery();

            if (appointmentRs.next()) {
                throw new AppointmentAlreadyExistsException("An appointment already exists for this doctor and patient on the given date.");
            }

            // Check if the doctor is available on the specified date
            checkScheduleStmt.setInt(1, doctorId);
            checkScheduleStmt.setDate(2, new java.sql.Date(appointmentDate.getTime()));
            checkScheduleStmt.setDate(3, new java.sql.Date(appointmentDate.getTime()));
            ResultSet scheduleRs = checkScheduleStmt.executeQuery();

            if (!scheduleRs.next()) {
                // Doctor is not available
                throw new RuntimeException("Doctor Not available");
            }

            // If doctor is available, book the appointment
            try (PreparedStatement insertStmt = conn.prepareStatement(insertAppointmentQuery)) {
                insertStmt.setInt(1, doctorId);
                insertStmt.setInt(2, patientId);
                insertStmt.setDate(3, new java.sql.Date(appointmentDate.getTime()));
                insertStmt.executeUpdate();
                return true;
            }
        }
//        return false;
    }
}
