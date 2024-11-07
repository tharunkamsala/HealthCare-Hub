package com.demo.storage;

import com.demo.exceptions.*;
import com.demo.storage.models.*;
import com.demo.storage.interfaces.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDaoImpl implements DoctorDao {
    private final String url = "jdbc:mysql://localhost:3306/hms";
    private final String username = "root";
    private final String password = "root";

    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url,username,password);
    }

    @Override
    public void addSchedule(Schedule schedule) throws ScheduleExistsException, SQLException {
        String checkQuery = "SELECT * FROM schedules WHERE doctor_id = ? AND start_date = ? AND end_date = ?";
        String insertQuery = "INSERT INTO schedules (doctor_id, start_date, end_date, is_available) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            checkStmt.setInt(1, schedule.getDoctorId());
            checkStmt.setDate(2, new Date(schedule.getStartDate().getTime()));
            checkStmt.setDate(3, new Date(schedule.getEnddate().getTime()));
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                throw new ScheduleExistsException("Schedule already exists for the given doctor within the specified dates.");
            }

            insertStmt.setInt(1, schedule.getDoctorId());
            insertStmt.setDate(2, new Date(schedule.getStartDate().getTime()));
            insertStmt.setDate(3, new Date(schedule.getEnddate().getTime()));
            insertStmt.setBoolean(4, schedule.isAvailable());
            insertStmt.executeUpdate();
        } catch (SQLException | ScheduleExistsException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void updateSchedule(Schedule schedule) throws ScheduleNotFoundException, SQLException {
        String query = "UPDATE schedules SET start_date = ?, end_date = ?, is_available = ? WHERE doctor_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, new Date(schedule.getStartDate().getTime()));
            stmt.setDate(2, new Date(schedule.getEnddate().getTime()));
            stmt.setBoolean(3, schedule.isAvailable());
            stmt.setInt(4, schedule.getDoctorId());

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new ScheduleNotFoundException("No schedule found for the given doctor.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Appointment> getAppointmentsByDoctorId(int doctorId) throws DoctorNotFoundException, SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM appointments WHERE doctor_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(rs.getInt("id"));
                appointment.setDoctorId(rs.getInt("doctor_id"));
                appointment.setPatientId(rs.getInt("patient_id"));
                appointment.setAppointmentDate(rs.getDate("appointment_date"));
                appointment.setReasonForVisit(rs.getString("reason_for_visit"));
                appointment.setStatus(rs.getString("status"));

//                Adding this into the list
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public void cancelAppointment(int appointmentId) throws AppointmentNotFoundException, SQLException {
        String query = "DELETE FROM appointments WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, appointmentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addMedicationsToAppointment(int appointmentId, List<Medication> medications) throws AppointmentNotFoundException, SQLException{
        String query = "INSERT INTO medications (appointment_id, name, dosage) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            for (Medication medication : medications) {
                stmt.setInt(1, appointmentId);
                stmt.setString(2, medication.getName());
                stmt.setString(3, medication.getDosage());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTestsToAppointment(int appointmentId, List<Tests> tests) throws AppointmentNotFoundException, SQLException{
        String query = "INSERT INTO tests (appointment_id, test_name, instructions) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            for (Tests test : tests) {
                stmt.setInt(1, appointmentId);
                stmt.setString(2, test.getName());
                stmt.setString(3, test.getInstructions());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
