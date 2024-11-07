
# HealthCare Hub

## Project Description

The Hospital Management System (HMS) is a comprehensive software solution designed to streamline and automate the day-to-day operations of a hospital. This system manages various aspects of hospital administration, including scheduling, appointment management, and patient care. It provides distinct functionalities for admins, doctors, and receptionists to enhance efficiency and ensure smooth operations within the hospital environment.

## Features

### Admin Features
- **Manage Doctors:** Add, update, and remove doctor records, including details like specialty and contact information.
- **Manage Appointments:** Schedule, update, and cancel patient appointments. Ensure appointment cancellations adhere to system rules.
- **Doctor Schedule Management:** View and modify doctor availability and schedules.
- **Reporting:** Generate and view reports on doctors, appointments, and patients.

### Doctor Features
- **Schedule Management:** Set up and update personal schedules, including availability.
- **Appointment Management:** View and cancel appointments. Access details about upcoming appointments.
- **Patient Care:** Suggest future medications and tests based on patient consultations.

### Receptionist (User) Features
- **Patient Registration:** Register new patients and maintain patient records.
- **Appointment Booking:** Schedule appointments for patients with doctors, ensuring adherence to doctor availability.

## Technology Stack
- **Backend:** Java
- **Database:** MySQL, JDBC
- **Testing:** JUnit 5
- **Frontend:** HTML, CSS, JavaScript

## Prerequisites

Before you begin, ensure you have the following installed on your machine:

- **Java Development Kit (JDK)** 1.8 or higher
- **Apache Maven** (for build automation)
- **MySQL** (for the database)
- **Apache Tomcat** or another Java web server (optional, if deploying as a web application)
- **Git** (optional, for version control)
- **A modern web browser** (for accessing the frontend)

## Installation

### Clone the Repository

```bash
git clone https://github.com/yourusername/hospital-management-system.git
cd hospital-management-system
```

### Database Setup

1. **Start MySQL Server**: Ensure your MySQL server is running.

2. **Create Database**:
   
   Log in to the MySQL command line interface or use a GUI like phpMyAdmin, and create a new database:
   
   ```sql
   CREATE DATABASE hospital_management;
   ```
3. Import Database Schema:

Import the provided SQL file to set up the required tables and schema.

```bash
mysql -u root -p hospital_management < database/schema.sql
```
4. Update Database Configuration:

Update the database configuration in your Java backend project. Locate the db.properties file and ensure the following properties are set:

```bash
db.url=jdbc:mysql://localhost:3306/hospital_management
db.username=root
db.password=your_password
```
5. Run the project

