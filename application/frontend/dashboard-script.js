document.addEventListener("DOMContentLoaded", function() {
    const doctorList = document.getElementById("doctor-list");
    const appointmentList = document.getElementById("appointment-list");
    const reportContent = document.getElementById("report-content");

    // Handle Doctor Registration
    document.getElementById("register-doctor-form").addEventListener("submit", function(event) {
        event.preventDefault();
        const doctorName = document.getElementById("doctor-name").value;
        const doctorSpecialty = document.getElementById("doctor-specialty").value;

        const doctorItem = document.createElement("li");
        doctorItem.textContent = `${doctorName} - ${doctorSpecialty}`;
        doctorList.appendChild(doctorItem);

        document.getElementById("doctor-name").value = "";
        document.getElementById("doctor-specialty").value = "";

        updateDoctorOptions(doctorName);
    });

    // Handle Appointment Creation
    document.getElementById("appointment-form").addEventListener("submit", function(event) {
        event.preventDefault();
        const selectedDoctor = document.getElementById("select-doctor").value;
        const appointmentDate = document.getElementById("appointment-date").value;

        const appointmentItem = document.createElement("li");
        appointmentItem.textContent = `${selectedDoctor} - ${appointmentDate}`;
        appointmentList.appendChild(appointmentItem);

        document.getElementById("appointment-date").value = "";
    });

    // Update Doctor Options for Appointments
    function updateDoctorOptions(doctorName) {
        const selectDoctor = document.getElementById("select-doctor");
        const newOption = document.createElement("option");
        newOption.value = doctorName;
        newOption.textContent = doctorName;
        selectDoctor.appendChild(newOption);
    }

    // Generate Doctor Report
    document.getElementById("generate-report").addEventListener("click", function() {
        let reportText = "Doctors Report:\n";
        const doctors = doctorList.querySelectorAll("li");
        doctors.forEach(doctor => {
            reportText += `${doctor.textContent}\n`;
        });
        reportContent.textContent = reportText;
    });

    // Generate Appointment Report
    document.getElementById("generate-patient-report").addEventListener("click", function() {
        let reportText = "Appointments Report:\n";
        const appointments = appointmentList.querySelectorAll("li");
        appointments.forEach(appointment => {
            reportText += `${appointment.textContent}\n`;
        });
        reportContent.textContent = reportText;
    });

    // Logout Functionality
    document.getElementById("logout").addEventListener("click", function() {
        window.location.href = "admin.html";
    });
});
