const baseURL = "https://jubilant-garbanzo-r4rr96r99pr4fx794-8080.app.github.dev";

// Login function (Admin & User)
function login() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    fetch(`${baseURL}/api/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
    })
    .then(res => res.json())
    .then(data => {
        if (data.error) {
            alert(data.error);
            return;
        }

        localStorage.setItem("username", data.username);
        localStorage.setItem("role", data.role);

        if (data.role === "ADMIN") {
            window.location.href = "admin-dashboard.html";
        } else {
            window.location.href = "dashboard.html";
        }
    })
    .catch(err => {
        console.error(err);
        alert("Login failed");
    });
}

// Registration function (User)
function register() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    fetch(`${baseURL}/api/auth/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
    })
    .then(res => res.json())
    .then(data => {
        const msg = document.getElementById("message");
        if (data.error) {
            msg.textContent = data.error;
            msg.className = "error";
            return;
        }

        msg.textContent = "Registration successful! Redirecting to login...";
        msg.className = "success";
        setTimeout(() => { window.location.href = "login.html"; }, 2000);
    })
    .catch(err => {
        console.error(err);
        alert("Registration failed");
    });
}

// Submit complaint (User)
function submitComplaint() {
    const username = localStorage.getItem("username");
    const category = document.getElementById("category").value;
    const description = document.getElementById("description").value;

    fetch(`${baseURL}/api/complaints/submit`, {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: `username=${encodeURIComponent(username)}&category=${encodeURIComponent(category)}&description=${encodeURIComponent(description)}`
    })
    .then(res => res.text())
    .then(alert)
    .finally(() => {
        document.getElementById("category").value = "";
        document.getElementById("description").value = "";
        loadComplaints();
    });
}

function clearAllComplaints() {
    if (!confirm("Are you sure you want to clear all complaints?")) return;

    fetch(`${baseURL}/api/admin/clearAll`, { method: "POST" })
        .then(res => res.text())
        .then(alert)
        .finally(loadComplaints);
}

// Load complaints (User & Admin)
function loadComplaints() {
    const username = localStorage.getItem("username");
    const role = localStorage.getItem("role");

    let url;
    if (role === "ADMIN") {
        url = `${baseURL}/api/admin/complaints`;
    } else {
        url = `${baseURL}/api/complaints/user?username=${username}`;
    }

    fetch(url)
        .then(res => res.json())
        .then(data => {
            const tbody = document.querySelector("#complaintTable tbody");
            tbody.innerHTML = "";

            data.forEach(c => {
                const row = role === "ADMIN"
                    ? `<tr>
                        <td>${c.id}</td>
                        <td>${c.user.username}</td>
                        <td>${c.category}</td>
                        <td>${c.description}</td>
                        <td>${c.status}</td>
                        <td>${c.assignedTo ? c.assignedTo.username : ''}</td>
                        <td>
                            <input type="text" placeholder="IT username" id="it-${c.id}">
                            <button onclick="assignIT(${c.id})">Assign</button>
                        </td>
                        <td>
                            <select id="status-${c.id}">
                                <option value="IN_PROGRESS">In Progress</option>
                                <option value="RESOLVED">Resolved</option>
                                <option value="REJECTED">Rejected</option>
                            </select>
                            <button onclick="updateStatus(${c.id})">Update</button>
                        </td>
                        <td>
                            <button onclick="clearComplaint(${c.id})">Clear</button>
                        </td>
                    </tr>`
                    : `<tr>
                        <td>${c.id}</td>
                        <td>${c.category}</td>
                        <td>${c.description}</td>
                        <td>${c.status}</td>
                        <td><button onclick="clearComplaint(${c.id})">Clear</button></td>
                    </tr>`;
                tbody.innerHTML += row;
            });
        });
}

// Admin functions
function assignIT(id) {
    const itUsername = document.getElementById(`it-${id}`).value;
    fetch(`${baseURL}/api/admin/assign?complaintId=${id}&itUsername=${itUsername}`, { method: "POST" })
        .then(res => res.text())
        .then(alert)
        .finally(loadComplaints);
}

function updateStatus(id) {
    const status = document.getElementById(`status-${id}`).value;
    fetch(`${baseURL}/api/admin/updateStatus?complaintId=${id}&status=${status}`, { method: "POST" })
        .then(res => res.text())
        .then(alert)
        .finally(loadComplaints);
}

// Clear complaint (User & Admin)
function clearComplaint(id) {
    fetch(`${baseURL}/api/complaints/clear?complaintId=${id}`, { method: "POST" })
        .then(res => res.text())
        .then(alert)
        .finally(loadComplaints);
}

// On page load
window.onload = () => {
    const username = localStorage.getItem("username");
    if (!username && !window.location.href.includes("login.html") && !window.location.href.includes("register.html")) {
        window.location.href = "login.html";
        return;
    }

    const welcome = document.getElementById("welcome");
    if (welcome) welcome.textContent = `Welcome, ${localStorage.getItem("username")}`;

    if (document.querySelector("#complaintTable")) loadComplaints();
};
