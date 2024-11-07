package com.demo.storage;

import com.demo.exceptions.*;
import com.demo.storage.models.*;
import com.demo.storage.interfaces.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDaoImpl implements AdminDao {
    private Connection connection;

    public AdminDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addDoctor(Doctor doctor) throws DoctorExistsException, SQLException {
        String checkSql = "SELECT * FROM doctors WHERE email = ?";
        String insertSql = "INSERT INTO doctors(name, specialization, email) VALUES (?, ?, ?)";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setString(1, doctor.getEmail());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                throw new DoctorExistsException("Doctor with this email already exists.");
            }

            try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                insertStmt.setString(1, doctor.getName());
                insertStmt.setString(2, doctor.getSpecialisation());
                insertStmt.setString(3, doctor.getEmail());
                insertStmt.executeUpdate();
            }
        }
    }

    @Override
    public void updateDoctor(Doctor doctor) throws DoctorNotFoundException, SQLException {
        String sql = "UPDATE doctors SET name = ?, specialization = ?, email = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, doctor.getName());
            stmt.setString(2, doctor.getSpecialisation());
            stmt.setString(3, doctor.getEmail());
            stmt.setInt(4, doctor.getId());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new DoctorNotFoundException("Doctor with the given ID not found.");
            }
        }
    }

    @Override
    public void deleteDoctor(int doctorId) throws DoctorNotFoundException, SQLException {
        String sql = "DELETE FROM doctors WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, doctorId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new DoctorNotFoundException("Doctor with the given ID not found.");
            }
        }
    }


    @Override
    public void cancelAllAppointments(int doctorId) throws DoctorNotFoundException, SQLException {
        String checkDoctorSql = "SELECT * FROM doctors WHERE id = ?";
        String selectAppointmentsSql = "SELECT id FROM appointments WHERE doctor_id = ?";
        String checkMedicationsSql = "SELECT COUNT(*) FROM medications WHERE appointment_id = ?";
        String checkTestsSql = "SELECT COUNT(*) FROM tests WHERE appointment_id = ?";
        String deleteAppointmentsSql = "DELETE FROM appointments WHERE id = ?";

        try (PreparedStatement checkDoctorStmt = connection.prepareStatement(checkDoctorSql)) {
            checkDoctorStmt.setInt(1, doctorId);
            ResultSet doctorRs = checkDoctorStmt.executeQuery();

            // Check if the doctor exists
            if (!doctorRs.next()) {
                throw new DoctorNotFoundException("Doctor with the given ID not found.");
            }

            try (PreparedStatement selectAppointmentsStmt = connection.prepareStatement(selectAppointmentsSql)) {
                selectAppointmentsStmt.setInt(1, doctorId);
                ResultSet appointmentsRs = selectAppointmentsStmt.executeQuery();

                // Loop through all appointments for this doctor
                while (appointmentsRs.next()) {
                    int appointmentId = appointmentsRs.getInt("id");

                    // Check if there are medications or tests for this appointment
                    boolean hasMedications = checkIfAppointmentHasMedications(appointmentId, checkMedicationsSql);
                    boolean hasTests = checkIfAppointmentHasTests(appointmentId, checkTestsSql);

                    // If there are medications or tests, skip deletion
                    if (hasMedications || hasTests) {
                        System.out.println("Cannot cancel appointment with ID " + appointmentId + ": Medications or tests have already been prescribed.");
                        continue;
                    }

                    // Otherwise, cancel the appointment
                    try (PreparedStatement deleteStmt = connection.prepareStatement(deleteAppointmentsSql)) {
                        deleteStmt.setInt(1, appointmentId);
                        deleteStmt.executeUpdate();
                        System.out.println("Canceled appointment with ID " + appointmentId);
                    }
                }
            }
        }
    }

    public boolean checkIfAppointmentHasMedications(int appointmentId, String checkMedicationsSql) throws SQLException {
        try (PreparedStatement checkMedicationsStmt = connection.prepareStatement(checkMedicationsSql)) {
            checkMedicationsStmt.setInt(1, appointmentId);
            ResultSet rs = checkMedicationsStmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Return true if medications exist
            }
        }
        return false;
    }

    public boolean checkIfAppointmentHasTests(int appointmentId, String checkTestsSql) throws SQLException {
        try (PreparedStatement checkTestsStmt = connection.prepareStatement(checkTestsSql)) {
            checkTestsStmt.setInt(1, appointmentId);
            ResultSet rs = checkTestsStmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Return true if tests exist
            }
        }
        return false;
    }

    @Override
    public void addSchedule(Schedule schedule) throws SQLException {
        String sql = "INSERT INTO schedules (doctor_id, start_date, end_date, is_available) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, schedule.getDoctorId());
            stmt.setDate(2, new Date(schedule.getStartDate().getTime()));
            stmt.setDate(3, new Date(schedule.getEnddate().getTime()));
            stmt.setBoolean(4, schedule.isAvailable());
            stmt.executeUpdate();
        }
    }

    @Override
    public void updateSchedule(Schedule schedule) throws SQLException {
        String sql = "UPDATE schedules SET is_available = ? WHERE doctor_id = ? and start_date = ? and end_date = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(3, new Date(schedule.getStartDate().getTime()));
            stmt.setDate(4, new Date(schedule.getEnddate().getTime()));
            stmt.setBoolean(1, schedule.isAvailable());
            stmt.setInt(2, schedule.getDoctorId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void removeSchedule(int doctorId) throws SQLException {
        String sql = "DELETE FROM schedules WHERE doctor_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, doctorId);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Doctor> getAllDoctors() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctors";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setId(rs.getInt("id"));
                doctor.setName(rs.getString("name"));
                doctor.setSpecialisation(rs.getString("specialization"));
                doctor.setEmail(rs.getString("email"));
                doctors.add(doctor);
            }
        }
        return doctors;
    }

    @Override
    public List<Appointment> getAllAppointments() throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(rs.getInt("id"));
                appointment.setDoctorId(rs.getInt("doctor_id"));
                appointment.setPatientId(rs.getInt("patient_id"));
                appointment.setAppointmentDate(rs.getDate("appointment_date"));
                appointment.setStatus(rs.getString("status"));
                appointments.add(appointment);
            }
        }
        return appointments;
    }

    @Override
    public List<Pateint> getAllPatients() throws SQLException {
        List<Pateint> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pateint patient = new Pateint();
                patient.setId(rs.getInt("id"));
                patient.setName(rs.getString("name"));
                patient.setDao(rs.getDate("dob"));
                patient.setContactInfo(rs.getString("contact_info"));
                patients.add(patient);
            }
        }
        return patients;
    }
}
