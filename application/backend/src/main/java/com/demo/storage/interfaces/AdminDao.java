package com.demo.storage.interfaces;



import com.demo.exceptions.DoctorExistsException;
import com.demo.exceptions.DoctorNotFoundException;
import com.demo.storage.models.Appointment;
import com.demo.storage.models.Doctor;
import com.demo.storage.models.Pateint;
import com.demo.storage.models.Schedule;

import java.sql.SQLException;
import java.util.List;

public interface AdminDao {
    void addDoctor(Doctor doctor) throws DoctorExistsException, SQLException;
    void updateDoctor(Doctor doctor) throws DoctorNotFoundException, SQLException;
    void deleteDoctor(int doctorId) throws DoctorNotFoundException, SQLException;
    void cancelAllAppointments(int doctorId) throws DoctorNotFoundException, SQLException;
    void addSchedule(Schedule schedule) throws SQLException;
    void updateSchedule(Schedule schedule) throws SQLException;
    void removeSchedule(int doctorId) throws SQLException;
    boolean checkIfAppointmentHasMedications(int appointmentId, String checkMedicationsSql) throws SQLException;
    boolean checkIfAppointmentHasTests(int appointmentId, String checkMedicationsSql) throws SQLException;

    List<Doctor> getAllDoctors() throws SQLException;
    List<Appointment> getAllAppointments() throws SQLException;
    List<Pateint> getAllPatients() throws SQLException;
}
