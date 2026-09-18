# JAVA-project
**Hospital Queue Management System** is a Java-based system that manages patient registration and queues. It prioritizes **emergency patients, senior citizens, and normal patients** to reduce waiting time and improve hospital efficiency.


## 📌 Description

The **Hospital Queue Management System** is a Java-based application designed to manage patient registration and hospital queues efficiently. It uses a priority system to ensure that patients are served in the correct order.

### Priority System

1. 🚨 **Emergency** – Highest Priority
2. 👴 **Senior Citizen (60+)**
3. 👤 **Normal Patient**

This system helps reduce waiting time and makes patient management more organized.

---

## ✨ Features

* Patient registration
* Automatic token generation
* Multiple hospital departments
* Priority-based queue management
* Emergency patient handling
* Senior citizen priority
* View current queue
* Call next patient
* Complete patient consultation
* Cancel patient token
* SQLite database for storing data
* Hospital staff dashboard APIs

---

## 🏥 Departments

The system includes:

* General Medicine
* Cardiology
* Orthopedics
* Pediatrics
* ENT
* Emergency

---

## 🛠️ Technologies Used

* **Java 17+**
* **Spring Boot**
* **SQLite**
* **Maven**
* **REST API**
* **VS Code**

---

## 📂 Project Structure

```text
HospitalQueueManagement/
│
├── pom.xml
│
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── hospital/
        │           └── queue/
        │               ├── HospitalQueueApplication.java
        │               ├── Database.java
        │               ├── HospitalService.java
        │               └── HospitalController.java
        │
        └── resources/
            └── application.properties
```

---

## ⚙️ Requirements

Before running the project, install:

* JDK 17 or higher
* Maven
* Visual Studio Code
* Java Extension Pack for VS Code

Check Java:

```bash
java -version
```

Check Maven:

```bash
mvn -version
```

---

## ▶️ How to Run

### 1. Open the project in VS Code

Open the `HospitalQueueManagement` folder.

### 2. Open the terminal

Run:

```bash
mvn spring-boot:run
```

### 3. Open the application

Go to:

```text
http://localhost:8080
```

The SQLite database will be created automatically as:

```text
hospital_queue.db
```

---

## 🔗 API Endpoints

### Get Departments

```http
GET /api/departments
```

### Register Patient

```http
POST /api/register
```

Example:

```json
{
    "name": "Rahul",
    "age": 22,
    "gender": "Male",
    "contact": "9876543210",
    "departmentId": 1,
    "emergency": false
}
```

### View Queue

```http
GET /api/queue/{departmentId}
```

### View Dashboard

```http
GET /api/dashboard/{departmentId}
```

### Call Next Patient

```http
POST /api/call-next/{departmentId}
```

### Complete Consultation

```http
POST /api/complete/{tokenId}
```

### Cancel Token

```http
POST /api/cancel/{tokenId}
```

---

## 🔄 Queue Logic

The system automatically calculates patient priority:

```text
Emergency
    ↓
Senior Citizen (60+)
    ↓
Normal Patient
```

For patients with the same priority, the patient with the **smaller token number is served first**.

### Example

```text
Token 1 → Normal
Token 2 → Emergency
Token 3 → Senior Citizen
Token 4 → Emergency
```

Service order:

```text
Token 2 → Emergency
Token 4 → Emergency
Token 3 → Senior Citizen
Token 1 → Normal
```

---

## 💾 Database

The system uses **SQLite** to store:

* Patients
* Departments
* Doctors
* Tokens
* Queue status
* Registration time
* Called time
* Completion time

---

## 🎯 Objective

The main objective of this project is to provide a simple and efficient way to manage hospital queues using **Java, priority-based scheduling, and database management**.

---

## 🚀 Future Improvements

* Web-based user interface
* Doctor login
* Admin login
* Patient login
* SMS notifications
* Estimated waiting time
* Appointment scheduling
* Multiple doctors per department
* Patient history
* Reports and analytics

---

## 👨‍💻 Project

**Hospital Queue Management System**

Built using **Java + Spring Boot + SQLite**.

## Author

Uday Chauhan 
