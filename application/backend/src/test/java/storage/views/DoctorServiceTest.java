package storage.views;

import com.demo.exceptions.AppointmentNotFoundException;
import com.demo.exceptions.DoctorNotFoundException;
import com.demo.exceptions.ScheduleExistsException;
import com.demo.exceptions.ScheduleNotFoundException;
import com.demo.storage.models.Appointment;
import com.demo.storage.models.Medication;
import com.demo.storage.models.Schedule;
import com.demo.storage.models.Tests;
import com.demo.storage.views.DoctorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DoctorServiceTest {
    private Connection connection;
    private DoctorService doctorService;

    @BeforeEach
    void setUp() throws SQLException {
        // Setup the database connection
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "root");

        // Initialize DoctorService with real DoctorDao
        doctorService = new DoctorService(connection);

        // Set up the test database schema and data
        // Commented out to avoid actual data modification
        // setupTestData();
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    // Commented out to avoid actual data modification
    // void setupTestData() throws SQLException {
    //     // Insert initial data into the test database
    //     try (var stmt = connection.createStatement()) {
    //         stmt.execute("INSERT INTO doctors (id, name, specialization, email) VALUES (6, 'Dr. Smithiii', 'MD', 'dr.smithiii@example.com')");
    //         stmt.execute("INSERT INTO appointments (id, doctor_id, patient_id, appointment_date, status) VALUES (1, 1, 1, CURDATE(), 'Scheduled')");
    //     }
    // }

    @Test
    void testAddSchedule() throws SQLException, ScheduleExistsException {
        Schedule schedule = new Schedule();
        schedule.setDoctorId(6);
        schedule.setStartDate(new java.sql.Date(System.currentTimeMillis()));
        schedule.setEnddate(new java.sql.Date(System.currentTimeMillis() + 86400000));
        schedule.setAvailable(true);

        doctorService.addSchedule(schedule);

        // Verify the schedule was added
        try (var stmt = connection.createStatement();
             var rs = stmt.executeQuery("SELECT * FROM schedules WHERE doctor_id = 6")) {
            assertTrue(rs.next());
            assertEquals(6, rs.getInt("doctor_id"));
        }
    }

    @Test
    void testUpdateSchedule() throws SQLException, ScheduleNotFoundException {
        Schedule schedule = new Schedule();
        schedule.setDoctorId(1);
        schedule.setStartDate(new java.sql.Date(System.currentTimeMillis()));
        schedule.setEnddate(new java.sql.Date(System.currentTimeMillis() + 86400000));
        schedule.setAvailable(true);

        doctorService.addSchedule(schedule);

        schedule.setAvailable(false);
        doctorService.updateSchedule(schedule);

        // Verify the schedule was updated
        try (var stmt = connection.createStatement();
             var rs = stmt.executeQuery("SELECT * FROM schedules WHERE doctor_id = 1")) {
            assertTrue(rs.next());
            assertFalse(rs.getBoolean("is_available"));
        }
    }

    @Test
    void testGetAppointmentsByDoctorId() throws SQLException, DoctorNotFoundException {
        List<Appointment> appointments = doctorService.getAppointmentsByDoctorId(1);

        assertNotNull(appointments);
        assertEquals(0, appointments.size());
        assertEquals(0, appointments.get(0).getDoctorId());
    }

    @Test
    void testCancelAppointmentWithMedications() throws SQLException, AppointmentNotFoundException {
        // Assumes appointment with ID 1 has associated medications
        try (var stmt = connection.createStatement();
             var rs = stmt.executeQuery("SELECT COUNT(*) FROM medications WHERE appointment_id = 1")) {
            if (rs.next() && rs.getInt(1) > 0) {
                doctorService.cancelAppointment(1);
                // Verify that appointment was not canceled
                try (var rs2 = stmt.executeQuery("SELECT * FROM appointments WHERE id = 1")) {
                    assertTrue(rs2.next());
                }
            }
        }
    }

    @Test
    void testCancelAppointmentWithTests() throws SQLException, AppointmentNotFoundException {
        // Assumes appointment with ID 1 has associated tests
        try (var stmt = connection.createStatement();
             var rs = stmt.executeQuery("SELECT COUNT(*) FROM tests WHERE appointment_id = 1")) {
            if (rs.next() && rs.getInt(1) > 0) {
                doctorService.cancelAppointment(1);
                // Verify that appointment was not canceled
                try (var rs2 = stmt.executeQuery("SELECT * FROM appointments WHERE id = 1")) {
                    assertTrue(rs2.next());
                }
            }
        }
    }

    @Test
    void testCancelAppointmentWithoutMedicationsOrTests() throws SQLException, AppointmentNotFoundException {
        // Assumes appointment with ID 2 does not have associated medications or tests
        doctorService.cancelAppointment(2);

        // Verify the appointment was canceled
        try (var stmt = connection.createStatement();
             var rs = stmt.executeQuery("SELECT * FROM appointments WHERE id = 2")) {
            assertFalse(rs.next());
        }
    }

    @Test
    void testAddMedicationsToAppointment() throws SQLException, AppointmentNotFoundException {
        List<Medication> medications = new ArrayList<>();
        Medication medication = new Medication();
        medication.setName("Aspirin");
        medication.setDosage("100mg");
        medications.add(medication);

        doctorService.addMedicationsToAppointment(1, medications);

        // Verify the medication was added
        try (var stmt = connection.createStatement();
             var rs = stmt.executeQuery("SELECT * FROM medications WHERE appointment_id = 1")) {
            assertTrue(rs.next());
            assertEquals("Aspirin", rs.getString("name"));
        }
    }

    @Test
    void testAddTestsToAppointment() throws SQLException, AppointmentNotFoundException {
        List<Tests> tests = new ArrayList<>();
        Tests test = new Tests();
        test.setName("Blood Test");
        test.setInstructions("Fast for 8 hours");
        tests.add(test);

        doctorService.addTestsToAppointment(1, tests);

        // Verify the test was added
        try (var stmt = connection.createStatement();
             var rs = stmt.executeQuery("SELECT * FROM tests WHERE appointment_id = 1")) {
            assertTrue(rs.next());
            assertEquals("Blood Test", rs.getString("test_name"));
        }
    }
}
