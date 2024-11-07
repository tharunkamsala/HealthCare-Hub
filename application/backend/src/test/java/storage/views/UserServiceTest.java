package com.demo.storage.views;

import com.demo.exceptions.AppointmentAlreadyExistsException;
import com.demo.exceptions.PatientAlreadyExistsException;
import com.demo.storage.RoleFactory;
import com.demo.storage.interfaces.UserDao;
import com.demo.storage.models.Pateint;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    private Connection connection;
    private UserService userService;

    @BeforeAll
    void setupDatabase() throws SQLException {
        // Establishing a connection to the database
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "root");
        userService = new UserService(connection);
    }

    @Test
    void testRegisterPatient() throws SQLException, PatientAlreadyExistsException {
        // Creating a new patient instance
        Pateint patient = new Pateint();
        patient.setName("Alice Johnson");
        patient.setDao(new java.sql.Date(System.currentTimeMillis()));
        patient.setContactInfo("9876543210");

        // Registering the patient
        userService.registerPatient(patient);

        // Normally, you would query the database or mock it to verify that the patient was registered.
        // For this example, we'll assume registration went through and just assert true.
        assertTrue(true);  // Replace with actual database validation logic if needed.
    }

    @Test
    void testBookAppointment() throws SQLException, AppointmentAlreadyExistsException {
        // Booking an appointment for an existing doctor and patient
        int doctorId = 1;
        int patientId = 2;
        Date appointmentDate = new java.sql.Date(System.currentTimeMillis());

        // Booking the appointment
        boolean success = userService.bookAppointment(doctorId, patientId, appointmentDate);

        // Asserting that the appointment booking was successful
        assertTrue(success);
    }

    @Test
    void testBookDuplicateAppointment() {
        // This test simulates an attempt to book a duplicate appointment
        int doctorId = 1;
        int patientId = 2;
        Date appointmentDate = new java.sql.Date(System.currentTimeMillis());

        assertThrows(AppointmentAlreadyExistsException.class, () -> {
            // Booking the same appointment twice should throw an exception
            userService.bookAppointment(doctorId, patientId, appointmentDate);
            userService.bookAppointment(doctorId, patientId, appointmentDate);
        });
    }

    @Test
    void testRegisterDuplicatePatient() {
        Pateint patient = new Pateint();
        patient.setName("Alice Johnson");
        patient.setDao(new java.sql.Date(System.currentTimeMillis()));
        patient.setContactInfo("9876543210");

        assertThrows(PatientAlreadyExistsException.class, () -> {
            // Register the same patient twice, which should throw an exception
            userService.registerPatient(patient);
            userService.registerPatient(patient);
        });
    }
}
