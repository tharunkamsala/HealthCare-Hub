package com.demo;

import com.demo.exceptions.*;
import com.demo.storage.RoleFactory;
import com.demo.storage.models.*;
import com.demo.storage.interfaces.*;
import com.demo.storage.views.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hms";
        String username = "root";
        String password = "root";

        Connection connection = null;

        try {
            // Establishing a database connection
            connection = DriverManager.getConnection(url, username, password);

            // Creating an instance of AdminService
            AdminService adminService = new AdminService(connection);
//
//             Example of adding a new doctor
            Doctor newDoctor = new Doctor(20, "Dr. Sravya Somu", "Pediatrician", "sravyasomu@gmail.com");
            try {
                adminService.addDoctor(newDoctor);
                System.out.println("Doctor added successfully.");
            } catch (DoctorExistsException | SQLException e) {
                System.out.println("Error adding doctor: " + e.getMessage());
            }
//
           // Example of updating an existing doctor
            Doctor updatedDoctor = new Doctor(9, "Dr. Nithin Kumar Yadla", "Cardiology", "nithinkumar@gmail.com");
            try {
                adminService.updateDoctor(updatedDoctor);
                System.out.println("Doctor updated successfully.");
            } catch (DoctorNotFoundException | SQLException e) {
                System.out.println("Error updating doctor: " + e.getMessage());
            }
//
//            // Example of canceling all appointments for a doctor
            int doctorId = 12;
            try {
                adminService.cancelAllAppointments(doctorId);
                System.out.println("All appointments canceled for doctor with ID " + doctorId);
            } catch (DoctorNotFoundException | SQLException e) {
                System.out.println("Error canceling appointments: " + e.getMessage());
            }

//            // Example of adding a new schedule
            Schedule newSchedule = new Schedule(1, new Date(), new Date(), true);
            try {
                adminService.addSchedule(newSchedule);
                System.out.println("Schedule added successfully.");
            } catch (SQLException e) {
                System.out.println("Error adding schedule: " + e.getMessage());
            }

            // Example of updating an existing schedule
            Schedule updatedSchedule = new Schedule(1, new Date(), new Date(), false);
            try {
                adminService.updateSchedule(updatedSchedule);
                System.out.println("Schedule updated successfully.");
            } catch (SQLException e) {
                System.out.println("Error updating schedule: " + e.getMessage());
            }
//
//            // Example of removing a schedule
            int scheduleDoctorId = 1;
            try {
                adminService.removeSchedule(scheduleDoctorId);
                System.out.println("Schedule removed successfully for doctor with ID " + scheduleDoctorId);
            } catch (SQLException e) {
                System.out.println("Error removing schedule: " + e.getMessage());
            }
//
//             Example of getting all doctors
            try {
                List<Doctor> doctors = adminService.getAllDoctors();
                System.out.println("Doctors:");
                for (Doctor doctor : doctors) {
                    System.out.println(doctor);
                }
            } catch (SQLException e) {
                System.out.println("Error retrieving doctors: " + e.getMessage());
            }
//
//            // Example of getting all appointments
            try {
                List<Appointment> appointments = adminService.getAllAppointments();
                System.out.println("Appointments:");
                for (Appointment appointment : appointments) {
                    System.out.println(appointment);
                }
            } catch (SQLException e) {
                System.out.println("Error retrieving appointments: " + e.getMessage());
            }

            // Example of getting all patients
            try {
                List<Pateint> patients = adminService.getAllPatients();
                System.out.println("Patients:");
                for (Pateint patient : patients) {
                    System.out.println(patient);
                }
            } catch (SQLException e) {
                System.out.println("Error retrieving patients: " + e.getMessage());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Ensure the connection is closed
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            // Establish database connection
            connection = DriverManager.getConnection(url, username, password);

            // Use RoleFactory to get the appropriate DoctorDao implementation
            DoctorDao doctorDao = (DoctorDao) RoleFactory.getRoleImplementation(RoleFactory.DOCTOR_ROLE, connection);

            // Instantiate DoctorService with the obtained DoctorDao
            DoctorService doctorService = new DoctorService(connection);

            // Example usage of DoctorService methods
            // 1. Adding a new schedule
            Schedule newSchedule = new Schedule(1, new Date(), new Date(), true);
            doctorService.addSchedule(newSchedule);

            // 2. Updating an existing schedule
            Schedule updatedSchedule = new Schedule(1, new Date(), new Date(), false);
            doctorService.updateSchedule(updatedSchedule);

            // 3. Retrieving appointments by doctor ID
            List<Appointment> appointments = doctorService.getAppointmentsByDoctorId(9);
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
            }

            // 4. Canceling an appointment
            doctorService.cancelAppointment(1);

            // 5. Adding medications to an appointment
            // Assuming you have a list of medications to add
            List<Medication> medications = List.of(new Medication("Aspirin", "500mg"));
            doctorService.addMedicationsToAppointment(1, medications);

            // 6. Adding tests to an appointment
            // Assuming you have a list of tests to add
            List<Tests> tests = List.of(new Tests("Blood Test", "Fast for 8 hours"));
            doctorService.addTestsToAppointment(1, tests);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // Establish database connection
            connection = DriverManager.getConnection(url, username, password);

            // Use RoleFactory to get the appropriate UserDao implementation
            UserDao userDao = (UserDao) RoleFactory.getRoleImplementation(RoleFactory.USER_ROLE, connection);

            // Instantiate UserService with the obtained UserDao
            UserService userService = new UserService(connection);

//             Example usage of UserService methods
//             1. Registering a new patient
            Pateint newPatient = new Pateint("John Doe", new Date(), "123-456-7880");
            try {
                userService.registerPatient(newPatient);
                System.out.println("Patient registered successfully.");
            } catch (PatientAlreadyExistsException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (SQLException e) {
                e.printStackTrace();
            }

//             2. Booking an appointment
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = "2024-08-23";

                Date appointmentDate = dateFormat.parse(dateString);

                boolean success = userService.bookAppointment(4, 2, appointmentDate);
                if (success) {
                    System.out.println("Appointment booked successfully.");
                } else {
                    System.out.println("Failed to book appointment.");
                }
            } catch (AppointmentAlreadyExistsException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
