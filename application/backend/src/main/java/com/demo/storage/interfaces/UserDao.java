package com.demo.storage.interfaces;



import com.demo.exceptions.AppointmentAlreadyExistsException;
import com.demo.exceptions.PatientAlreadyExistsException;
import com.demo.storage.models.Pateint;

import java.sql.SQLException;

public interface UserDao {
    public void registerPatient(Pateint pateint) throws SQLException, PatientAlreadyExistsException;
    public boolean bookAppointment(int doctorId, int patientId, java.util.Date appointmentDate) throws AppointmentAlreadyExistsException, SQLException;
}
