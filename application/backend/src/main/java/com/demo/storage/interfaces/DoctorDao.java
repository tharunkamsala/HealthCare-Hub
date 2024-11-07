package com.demo.storage.interfaces;



import com.demo.exceptions.AppointmentNotFoundException;
import com.demo.exceptions.DoctorNotFoundException;
import com.demo.exceptions.ScheduleExistsException;
import com.demo.exceptions.ScheduleNotFoundException;
import com.demo.storage.models.Appointment;
import com.demo.storage.models.Medication;
import com.demo.storage.models.Schedule;
import com.demo.storage.models.Tests;

import java.sql.SQLException;
import java.util.List;

public interface DoctorDao {
    public void addSchedule(Schedule schedule) throws ScheduleExistsException, SQLException;  // Inserts a new schedule into the database
    public void updateSchedule(Schedule schedule) throws ScheduleNotFoundException, SQLException;  // Updates the doctor's schedule in the database
    public List<Appointment> getAppointmentsByDoctorId(int doctorId) throws DoctorNotFoundException, SQLException;  // Retrieves appointments for the doctor
    public void cancelAppointment(int appointmentId) throws AppointmentNotFoundException, SQLException;  // Cancels a specific appointment
    public void addMedicationsToAppointment(int appointmentId, List<Medication> medications) throws AppointmentNotFoundException, SQLException;  // Suggests medications for an appointment
    public void addTestsToAppointment(int appointmentId, List<Tests> tests) throws AppointmentNotFoundException, SQLException;  // Suggests tests for an appointment
}
