document.getElementById("login-form").addEventListener("submit", function(event) {
    event.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    // Dummy validation for login
    if (username === "doctor" && password === "doctor123") {
        window.location.href = "doctor-dashboard.html";  // Redirect to a dummy dashboard
    } else {
        alert("Invalid credentials. Please try again.");
    }
});

document.getElementById("signup-form").addEventListener("submit", function(event) {
    event.preventDefault();

    const fullname = document.getElementById("fullname").value;
    const username = document.getElementById("username").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    // Here you would normally handle the signup process
    alert("Signup successful! You can now log in.");
    window.location.href = "doctor-login.html";  // Redirect to login page after signup
});
