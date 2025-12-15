# Complaint Management System

A web-based Complaint Management System built with **Spring Boot**, **Java**, **H2 Database**, and **JavaScript**.  
The system allows **Users** to submit complaints, **Admins** to manage and assign complaints, and **IT staff** to view and resolve assigned complaints.

---

## Features

### User
- Register and login
- Submit complaints with category and description
- View status of submitted complaints
- Clear complaints once resolved

### Admin
- View all user complaints
- Assign complaints to IT staff
- Update complaint status
- Clear complaints

### IT Staff
- Login to view assigned complaints
- Update complaint status

---

## Tech Stack
- **Backend:** Spring Boot (Java)
- **Database:** H2 (in-memory or file-based)
- **Frontend:** HTML, CSS, JavaScript (Vanilla JS)
- **Build Tool:** Maven

---
## Setup & Run Locally

### Prerequisites
- Java 17+
- Maven 3+
- Optional: IDE (IntelliJ, VSCode)  

### Steps

1. **Clone the repository**
```bash
git clone <your-repo-url>
cd complaint-system
```

2. **Build and run the Spring Boot app**
```bash
./mvnw spring-boot:run
```

Or, if Maven is installed globally:
```bash
mvn spring-boot:run
```

3. **Open the application in a browser**
- User login: ```http://localhost:8080/login.html```
- User register:``` http://localhost:8080/register.html```
- User dashboard: ```http://localhost:8080/dashboard.html```
- Admin dashboard: ```http://localhost:8080/admin-dashboard.html```
- H2 console (optional): ```http://localhost:8080/h2-console```
- JDBC URL: ```jdbc:h2:mem:testdb```
- Username: ```sa```
- Password: (leave empty)
  
### Usage Flow

1. **User**
- Register or login
- Submit complaints
- View and clear complaints

3. **Admin**
- Login using admin credentials
- View all complaints
- Assign complaints to IT users
- Update status (IN_PROGRESS, RESOLVED, REJECTED)
- Clear complaints

3. **IT**
- Login using IT credentials
- View assigned complaints
- Update status

### Database

- H2 Database is used for simplicity (in-memory DB by default).
- Tables: ```users```, ```complaints```.
- Predefined roles: USER, ADMIN, IT.
- You can add IT users directly in the DB via H2 console or registration endpoint.

### API Endpoints
**AuthController**
- ```POST /api/auth/register``` – Register a new user
- ```POST /api/auth/login``` – Login user/admin/IT

**ComplaintController**
- ```POST /api/complaints/submit ```– Submit complaint
- ```GET /api/complaints/user?username=``` – View user complaints
- ```POST /api/complaints/clear?complaintId=``` – Clear a complaint

**AdminController**
- ```GET /api/admin/complaints``` – View all complaints
- ```POST /api/admin/assign?complaintId=&itUsername=``` – Assign complaint to IT
- ```POST /api/admin/updateStatus?complaintId=&status=``` – Update complaint status
- ```GET /api/admin/complaints/assigned?username=``` – View complaints assigned to specific IT
