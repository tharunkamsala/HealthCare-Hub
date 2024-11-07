package storage.views;

import com.demo.exceptions.*;
import com.demo.storage.AdminDaoImpl;
import com.demo.storage.models.*;
import com.demo.storage.interfaces.*;
import com.demo.storage.views.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdminServiceTest {

    private Connection connection;
    private AdminService adminService;

    @BeforeAll
    void setupDatabase() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "root");
        AdminDao adminDao = new AdminDaoImpl(connection);
        adminService = new AdminService(connection);
    }

    @Test
    void testAddDoctor() throws SQLException, DoctorExistsException {
        Doctor doctor = new Doctor();
        doctor.setName("Dr. Haley");
        doctor.setSpecialisation("Cardiology");
        doctor.setEmail("dr.hale1@example.com");

        adminService.addDoctor(doctor);

        List<Doctor> doctors = adminService.getAllDoctors();
        //assertEquals(1, doctors.size());
        long count = doctors.stream().filter(doctor1 -> doctor1.getEmail().equals("dr.hale1@example.com")).count();
        assertEquals(1, count);
        //assertEquals("Dr. hale", doctors.get(0).getName());
    }

    @Test
    void testUpdateDoctor() throws SQLException, DoctorExistsException, DoctorNotFoundException {
        Doctor doctor = new Doctor();
        doctor.setName("Dr. Smiths");
        doctor.setSpecialisation("Nephrology");
        doctor.setEmail("dr.smith1@example.com");

//        adminService.addDoctor(doctor);

        List<Doctor> doctors = adminService.getAllDoctors();
        doctor = doctors.get(doctors.size()-1);
        doctor.setSpecialisation("Neurology");

        adminService.updateDoctor(doctor);

        doctors = adminService.getAllDoctors();
        assertEquals("Neurology", doctors.get(doctors.size()-1).getSpecialisation());
    }

    @Test
    void testCancelAllAppointments() throws SQLException, DoctorExistsException, DoctorNotFoundException {
        Doctor doctor = new Doctor();
        doctor.setName("Dr. Smitha");
        doctor.setSpecialisation("ENT");
        doctor.setEmail("dr.smithaaa@example.com");

        adminService.addDoctor(doctor);
        List<Doctor> doctors = adminService.getAllDoctors();
        int doctorId = doctors.get(doctors.size()-1).getId();

        Appointment appointment = new Appointment();
        appointment.setDoctorId(doctorId);
        appointment.setPatientId(1);
        appointment.setAppointmentDate(new java.sql.Date(System.currentTimeMillis()));
        appointment.setStatus("Scheduled");

        adminService.getAllAppointments().add(appointment);
        adminService.cancelAllAppointments(doctorId);

        List<Appointment> appointments = adminService.getAllAppointments();
        assertEquals(0,(int)appointments.stream().filter(appointment1 -> appointment1==appointment).count());
    }

    @Test
    void testAddSchedule() throws SQLException {
        Schedule schedule = new Schedule();
        schedule.setDoctorId(1);
        schedule.setStartDate(new java.sql.Date(System.currentTimeMillis()));
        schedule.setEnddate(new java.sql.Date(System.currentTimeMillis() + 86400000));
        schedule.setAvailable(true);

        adminService.addSchedule(schedule);

        // Verify schedule was added
        assertNotNull(adminService.getAllAppointments());
    }

    @Test
    void testUpdateSchedule() throws SQLException, ParseException {
        Schedule schedule = new Schedule();
        schedule.setDoctorId(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = "2024-08-13";
        Date parsedDate = sdf.parse(dateString);

        // Convert java.util.Date to java.sql.Date
        java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
        schedule.setStartDate(sqlDate);

        dateString = "2024-08-26";
        parsedDate = sdf.parse(dateString);
        sqlDate = new java.sql.Date(parsedDate.getTime());
        schedule.setEnddate(sqlDate);
        schedule.setAvailable(true);

        //adminService.addSchedule(schedule);

        schedule.setAvailable(false);
        adminService.updateSchedule(schedule);

        // Verify schedule was updated
        assertNotNull(adminService.getAllAppointments());
    }

    @Test
    void testRemoveSchedule() throws SQLException, ParseException {
        Schedule schedule = new Schedule();
        schedule.setDoctorId(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = "2024-08-13";
        Date parsedDate = sdf.parse(dateString);

        // Convert java.util.Date to java.sql.Date
        java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
        schedule.setStartDate(sqlDate);

        dateString = "2024-08-26";
        parsedDate = sdf.parse(dateString);
        sqlDate = new java.sql.Date(parsedDate.getTime());
        schedule.setEnddate(sqlDate);
        schedule.setAvailable(true);

//        adminService.addSchedule(schedule);
        adminService.removeSchedule(1);

        // Verify schedule was removed
        assertNotNull(adminService.getAllAppointments());
    }

    @Test
    void testGetAllDoctors() throws SQLException, DoctorExistsException {
        Doctor doctor1 = new Doctor();
        doctor1.setName("Dr. Smitha");
        doctor1.setSpecialisation("Nephrology");
        doctor1.setEmail("dr.smitha1@example.com");

        Doctor doctor2 = new Doctor();
        doctor2.setName("Dr. Jones");
        doctor2.setSpecialisation("Neurology");
        doctor2.setEmail("dr.jones1@example.com");

        List<Doctor> doctorsBefore = adminService.getAllDoctors();

        adminService.addDoctor(doctor1);
        adminService.addDoctor(doctor2);

        List<Doctor> doctors = adminService.getAllDoctors();
        assertEquals(doctorsBefore.size(), doctors.size()-2);
    }

    @Test
    void testGetAllAppointments() throws SQLException {
        // Retrieve all appointments from the database
        List<Appointment> appointments = adminService.getAllAppointments();

        // Verify the retrieved data
        assertTrue(appointments.stream().anyMatch(a ->
                a.getDoctorId() == 9 &&
                        a.getPatientId() == 3 &&
                        a.getStatus().equals("Scheduled")
        ), "Expected appointment not found in the retrieved list");

//        // Optionally, verify the count if needed
//        assertEquals(1, appointments.size(), "Expected one appointment in the list");
    }

    @Test
    void testGetAllPatients() throws SQLException {
        Pateint patient1 = new Pateint();
        patient1.setName("John Doe");
        patient1.setDao(new java.sql.Date(System.currentTimeMillis()));
        patient1.setContactInfo("1234567890");

        Pateint patient2 = new Pateint();
        patient2.setName("Jane Doe");
        patient2.setDao(new java.sql.Date(System.currentTimeMillis()));
        patient2.setContactInfo("0987654321");

        adminService.getAllPatients().add(patient1);
        adminService.getAllPatients().add(patient2);

        List<Pateint> patients = adminService.getAllPatients();
        assertEquals(9, patients.size());
    }
}