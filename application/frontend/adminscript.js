document.addEventListener("DOMContentLoaded", function() {
    // Login/Signup form toggling (only if on login page)
    const loginBtn = document.getElementById("login-btn");
    const signupBtn = document.getElementById("signup-btn");
    const loginForm = document.getElementById("login-form");
    const signupForm = document.getElementById("signup-form");
    const switchToSignup = document.getElementById("switch-to-signup");
    const switchToLogin = document.getElementById("switch-to-login");

    if (loginBtn && signupBtn && loginForm && signupForm && switchToSignup && switchToLogin) {
        loginBtn.addEventListener("click", function() {
            loginForm.classList.add("active-form");
            signupForm.classList.remove("active-form");
            loginBtn.classList.add("active");
            signupBtn.classList.remove("active");
        });

        signupBtn.addEventListener("click", function() {
            signupForm.classList.add("active-form");
            loginForm.classList.remove("active-form");
            signupBtn.classList.add("active");
            loginBtn.classList.remove("active");
        });

        switchToSignup.addEventListener("click", function(e) {
            e.preventDefault();
            signupForm.classList.add("active-form");
            loginForm.classList.remove("active-form");
            signupBtn.classList.add("active");
            loginBtn.classList.remove("active");
        });

        switchToLogin.addEventListener("click", function(e) {
            e.preventDefault();
            loginForm.classList.add("active-form");
            signupForm.classList.remove("active-form");
            loginBtn.classList.add("active");
            signupBtn.classList.remove("active");
        });

        document.getElementById('login-form').addEventListener('submit', function(event) {
            event.preventDefault();
            window.location.href = 'admin-dashboard.html'; // Redirect to dashboard on login
        });

        

        
    }

    // Logout button functionality (common across all dashboard pages)
    const logoutBtn = document.getElementById("logout");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", function() {
            window.location.href = 'index.html'; // Redirect to login page on logout
        });
    }

    const homeBtn = document.getElementById("homep");
    if (logoutBtn) {
        homeBtn.addEventListener("click", function() {
            window.location.href = 'index.html'; // Redirect to login page on logout
        });
    }

    // Additional functionality for managing accounts, appointments, and reports
    // These sections will only exist on their respective pages
    // const doctorForm = document.getElementById('register-doctor-form');
    // const appointmentForm = document.getElementById('appointment-form');
    const generateReportBtn = document.getElementById('generate-report');
    const generatePatientReportBtn = document.getElementById('generate-patient-report');

    // Manage doctor accounts logic
    // if (doctorForm) {
    //     doctorForm.addEventListener('submit', function(event) {
    //         event.preventDefault();
    //         const doctorName = document.getElementById('doctor-name').value;
    //         const doctorSpecialty = document.getElementById('doctor-specialty').value;
    //         const doctorList = document.getElementById('doctor-list');

    //         // Add new doctor to the list
    //         const newDoctor = document.createElement('li');
    //         newDoctor.textContent = `${doctorName} - ${doctorSpecialty}`;
    //         doctorList.appendChild(newDoctor);

    //         // Clear form fields
    //         doctorForm.reset();
    //     });
    // }




    const doctorForm = document.getElementById('register-doctor-form');
    const doctorList = document.getElementById('doctor-list');

    if (doctorForm) {
        doctorForm.addEventListener('submit', function(event) {
            event.preventDefault();
            const doctorName = document.getElementById('doctor-name').value;
            const doctorSpecialty = document.getElementById('doctor-specialty').value;

            if (doctorName && doctorSpecialty) {
                // Create new doctor list item with delete button
                const newDoctor = document.createElement('li');
                newDoctor.innerHTML = `${doctorName} - ${doctorSpecialty} <button class="delete-btn">Delete</button>`;

                // Append the new doctor to the list
                doctorList.appendChild(newDoctor);

                // Attach delete functionality to the new delete button
                const deleteBtn = newDoctor.querySelector('.delete-btn');
                deleteBtn.addEventListener('click', function() {
                    doctorList.removeChild(newDoctor);
                });

                // Clear form fields
                doctorForm.reset();
            } else {
                alert('Please fill in both fields before registering a doctor.');
            }
        });
    }

    // Attach delete functionality to existing doctors
    const deleteButtonss = document.querySelectorAll('.delete-btn');
    deleteButtonss.forEach(button => {
        button.addEventListener('click', function(event) {
            const listItem = event.target.parentElement;
            doctorList.removeChild(listItem);
        });
    });

    // Manage appointments logic
    // if (appointmentForm) {
    //     appointmentForm.addEventListener('submit', function(event) {
    //         event.preventDefault();
    //         const selectedDoctor = document.getElementById('select-doctor').value;
    //         const appointmentDate = document.getElementById('appointment-date').value;
    //         const appointmentList = document.getElementById('appointment-list');

    //         // Add new appointment to the list
    //         const newAppointment = document.createElement('li');
    //         newAppointment.textContent = `${selectedDoctor} - ${appointmentDate}`;
    //         appointmentList.appendChild(newAppointment);

    //         // Clear form fields
    //         appointmentForm.reset();
    //     });
    // }

    const appointmentForm = document.getElementById('appointment-form');
    const appointmentList = document.getElementById('appointment-list');

    if (appointmentForm) {
        appointmentForm.addEventListener('submit', function(event) {
            event.preventDefault();
            const selectedDoctor = document.getElementById('select-doctor').value;
            const appointmentDate = document.getElementById('appointment-date').value;

            if (selectedDoctor && appointmentDate) {
                // Create new appointment list item with delete button
                const newAppointment = document.createElement('li');
                newAppointment.innerHTML = `${selectedDoctor} - ${appointmentDate} <button class="delete-btn">Delete</button>`;

                // Append the new appointment to the list
                appointmentList.appendChild(newAppointment);

                // Attach delete functionality to the new delete button
                const deleteBtn = newAppointment.querySelector('.delete-btn');
                deleteBtn.addEventListener('click', function() {
                    appointmentList.removeChild(newAppointment);
                });

                // Clear form fields
                appointmentForm.reset();
            } else {
                alert('Please fill in both fields before adding an appointment.');
            }
        });
    }

    // Attach delete functionality to existing appointments
    const deleteButtons = document.querySelectorAll('.delete-btn');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            const listItem = event.target.parentElement;
            appointmentList.removeChild(listItem);
        });
    });

    // Generate reports logic
    if (generateReportBtn) {
        generateReportBtn.addEventListener('click', function() {
            const reportContent = document.getElementById('report-content');
            // Mock report generation for doctors
            reportContent.innerHTML = `
                <h3>Doctor Report</h3>
                <ul>
                    <li>Dr. John Smith - Cardiologist</li>
                    <li>Dr. Emily Watson - Neurologist</li>
                </ul>
            `;
        });
    }

    if (generatePatientReportBtn) {
        generatePatientReportBtn.addEventListener('click', function() {
            const reportContent = document.getElementById('report-content');
            // Mock report generation for appointments
            reportContent.innerHTML = `
                <h3>Appointment Report</h3>
                <ul>
                    <li>Dr. John Smith - 2024-08-20</li>
                    <li>Dr. Emily Watson - 2024-08-22</li>
                </ul>
            `;
        });
    }

    
    
});






















// document.addEventListener("DOMContentLoaded", function() {
//     // Login/Signup form toggling (only if on login page)
//     const loginBtn = document.getElementById("login-btn");
//     const signupBtn = document.getElementById("signup-btn");
//     const loginForm = document.getElementById("login-form");
//     const signupForm = document.getElementById("signup-form");
//     const switchToSignup = document.getElementById("switch-to-signup");
//     const switchToLogin = document.getElementById("switch-to-login");

//     if (loginBtn && signupBtn && loginForm && signupForm && switchToSignup && switchToLogin) {
//         loginBtn.addEventListener("click", function() {
//             loginForm.classList.add("active-form");
//             signupForm.classList.remove("active-form");
//             loginBtn.classList.add("active");
//             signupBtn.classList.remove("active");
//         });

//         signupBtn.addEventListener("click", function() {
//             signupForm.classList.add("active-form");
//             loginForm.classList.remove("active-form");
//             signupBtn.classList.add("active");
//             loginBtn.classList.remove("active");
//         });

//         switchToSignup.addEventListener("click", function(e) {
//             e.preventDefault();
//             signupForm.classList.add("active-form");
//             loginForm.classList.remove("active-form");
//             signupBtn.classList.add("active");
//             loginBtn.classList.remove("active");
//         });

//         switchToLogin.addEventListener("click", function(e) {
//             e.preventDefault();
//             loginForm.classList.add("active-form");
//             signupForm.classList.remove("active-form");
//             loginBtn.classList.add("active");
//             signupBtn.classList.remove("active");
//         });

        document.getElementById('login-form').addEventListener('submit', function(event) {
            event.preventDefault();
            if (validateLoginForm()) {
                window.location.href = 'admin-dashboard.html'; // Redirect to dashboard on login
            }
        });

        document.getElementById('signup-form').addEventListener('submit', function(event) {
            event.preventDefault();
            if (validateSignupForm()) {
                // Handle signup logic here (e.g., send data to server)
                alert('Signup successful!');
            }
        });

        function validateLoginForm() {
            const email = loginForm.querySelector('input[type="email"]').value;
            const password = loginForm.querySelector('input[type="password"]').value;

            if (!validateEmail(email)) {
                alert('Please enter a valid email address.');
                return false;
            }

            if (password.length < 6) {
                alert('Password must be at least 6 characters long.');
                return false;
            }

            return true;
        }

        function validateSignupForm() {
            const fullName = signupForm.querySelector('input[type="text"]').value;
            const email = signupForm.querySelector('input[type="email"]').value;
            const password = signupForm.querySelector('input[type="password"]').value;
            const confirmPassword = signupForm.querySelector('input[type="password"]:nth-of-type(2)').value;

            if (!fullName) {
                alert('Please enter your full name.');
                return false;
            }

            if (!validateEmail(email)) {
                alert('Please enter a valid email address.');
                return false;
            }

            if (password.length < 6) {
                alert('Password must be at least 6 characters long.');
                return false;
            }

            if (password !== confirmPassword) {
                alert('Passwords do not match.');
                return false;
            }

            return true;
        }

        function validateEmail(email) {
            // Basic email validation regex
            const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            return emailPattern.test(email);
        }

//         // Logout button functionality (common across all dashboard pages)
//         const logoutBtn = document.getElementById("logout");
//         if (logoutBtn) {
//             logoutBtn.addEventListener("click", function() {
//                 window.location.href = 'index.html'; // Redirect to login page on logout
//             });
//         }

//         // Additional functionality for managing accounts, appointments, and reports
//         const doctorForm = document.getElementById('register-doctor-form');
//         const doctorList = document.getElementById('doctor-list');

//         if (doctorForm) {
//             doctorForm.addEventListener('submit', function(event) {
//                 event.preventDefault();
//                 const doctorName = document.getElementById('doctor-name').value;
//                 const doctorSpecialty = document.getElementById('doctor-specialty').value;

//                 if (doctorName && doctorSpecialty) {
//                     // Create new doctor list item with delete button
//                     const newDoctor = document.createElement('li');
//                     newDoctor.innerHTML = `${doctorName} - ${doctorSpecialty} <button class="delete-btn">Delete</button>`;

//                     // Append the new doctor to the list
//                     doctorList.appendChild(newDoctor);

//                     // Attach delete functionality to the new delete button
//                     const deleteBtn = newDoctor.querySelector('.delete-btn');
//                     deleteBtn.addEventListener('click', function() {
//                         doctorList.removeChild(newDoctor);
//                     });

//                     // Clear form fields
//                     doctorForm.reset();
//                 } else {
//                     alert('Please fill in both fields before registering a doctor.');
//                 }
//             });
//         }

//         // Attach delete functionality to existing doctors
//         const deleteButtonss = document.querySelectorAll('.delete-btn');
//         deleteButtonss.forEach(button => {
//             button.addEventListener('click', function(event) {
//                 const listItem = event.target.parentElement;
//                 doctorList.removeChild(listItem);
//             });
//         });

//         // Manage appointments logic
//         const appointmentForm = document.getElementById('appointment-form');
//         const appointmentList = document.getElementById('appointment-list');

//         if (appointmentForm) {
//             appointmentForm.addEventListener('submit', function(event) {
//                 event.preventDefault();
//                 const selectedDoctor = document.getElementById('select-doctor').value;
//                 const appointmentDate = document.getElementById('appointment-date').value;

//                 if (selectedDoctor && appointmentDate) {
//                     // Create new appointment list item with delete button
//                     const newAppointment = document.createElement('li');
//                     newAppointment.innerHTML = `${selectedDoctor} - ${appointmentDate} <button class="delete-btn">Delete</button>`;

//                     // Append the new appointment to the list
//                     appointmentList.appendChild(newAppointment);

//                     // Attach delete functionality to the new delete button
//                     const deleteBtn = newAppointment.querySelector('.delete-btn');
//                     deleteBtn.addEventListener('click', function() {
//                         appointmentList.removeChild(newAppointment);
//                     });

//                     // Clear form fields
//                     appointmentForm.reset();
//                 } else {
//                     alert('Please fill in both fields before adding an appointment.');
//                 }
//             });
//         }

//         // Attach delete functionality to existing appointments
//         const deleteButtons = document.querySelectorAll('.delete-btn');
//         deleteButtons.forEach(button => {
//             button.addEventListener('click', function(event) {
//                 const listItem = event.target.parentElement;
//                 appointmentList.removeChild(listItem);
//             });
//         });

//         // Generate reports logic
//         const generateReportBtn = document.getElementById('generate-report');
//         const generatePatientReportBtn = document.getElementById('generate-patient-report');

//         if (generateReportBtn) {
//             generateReportBtn.addEventListener('click', function() {
//                 const reportContent = document.getElementById('report-content');
//                 // Mock report generation for doctors
//                 reportContent.innerHTML = `
//                     <h3>Doctor Report</h3>
//                     <ul>
//                         <li>Dr. John Smith - Cardiologist</li>
//                         <li>Dr. Emily Watson - Neurologist</li>
//                     </ul>
//                 `;
//             });
//         }

//         if (generatePatientReportBtn) {
//             generatePatientReportBtn.addEventListener('click', function() {
//                 const reportContent = document.getElementById('report-content');
//                 // Mock report generation for appointments
//                 reportContent.innerHTML = `
//                     <h3>Appointment Report</h3>
//                     <ul>
//                         <li>Dr. John Smith - 2024-08-20</li>
//                         <li>Dr. Emily Watson - 2024-08-22</li>
//                     </ul>
//                 `;
//             });
//         }
//     }
// });

