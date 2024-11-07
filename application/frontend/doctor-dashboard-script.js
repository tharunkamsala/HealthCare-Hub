// Schedule Form Handling
document.getElementById("schedule-form").addEventListener("submit", function(event) {
    event.preventDefault();

    const scheduleDate = document.getElementById("schedule-date").value;
    const scheduleList = document.getElementById("schedule-list");

    const newSchedule = document.createElement("li");
    newSchedule.textContent = scheduleDate;

    scheduleList.appendChild(newSchedule);

    document.getElementById("schedule-form").reset();
});

// Appointments Form Handling (No form, just displaying dummy data)

// Medication/Test Suggestion Form Handling
document.getElementById("medications-form").addEventListener("submit", function(event) {
    event.preventDefault();

    const patientName = document.getElementById("patient-name").value;
    const suggestion = document.getElementById("suggestion").value;
    const medicationsList = document.getElementById("medications-list");

    const newSuggestion = document.createElement("li");
    newSuggestion.textContent = `${patientName} - ${suggestion}`;

    medicationsList.appendChild(newSuggestion);

    document.getElementById("medications-form").reset();
});
