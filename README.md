# Hospital Management System (HMS)

A comprehensive Hospital Management System built with Java, featuring a client-server architecture using RMI (Remote Method Invocation) and MongoDB for data persistence. The system provides role-based access control for different hospital staff members and patients.

## 🏥 Features

### User Roles
The system supports six different user roles, each with specialized dashboards and functionalities:

1. **Admin** - System administration and user management
2. **Doctor** - Patient diagnosis, prescription management, and appointment handling
3. **Patient** - Appointment booking, viewing medical records, and prescription access
4. **Pharmacist** - Drug inventory management and prescription refill requests
5. **Lab Technician** - Lab test management and result recording
6. **Receptionist** - Patient registration and appointment scheduling

### Core Functionalities

#### Patient Management
- Patient registration and profile management
- Medical history tracking
- Patient record management
- View and update patient information

#### Appointment System
- Book, view, and manage appointments
- Doctor availability scheduling
- Appointment status tracking (Pending, Confirmed, Completed, Cancelled)
- Observer pattern implementation for appointment notifications

#### Prescription Management
- Create and manage prescriptions
- Medicine dosage tracking
- Read-only prescription view for patients
- Prescription history

#### Laboratory Management
- Request lab tests
- Record test results
- Track test status (Pending, Completed)
- View test history

#### ICU Management
- Request ICU admission
- Handle ICU requests with priority levels
- Track ICU room availability
- State pattern for ICU room status (Pending, Approved, Occupied, Released)

#### Pharmacy Management
- Drug inventory management
- Medicine refill requests
- Track drug availability
- Prescription fulfillment

#### Diagnosis & Medical Records
- Record patient diagnoses
- Clinical notes management
- Diagnosis history tracking
- Link diagnoses to appointments

#### Reporting
- Generate system reports
- View comprehensive hospital statistics
- Export data for analysis

## 🏗️ Architecture

### Technology Stack
- **Language**: Java 17
- **Architecture**: Client-Server (RMI)
- **Database**: MongoDB
- **GUI Framework**: Java Swing
- **Build Tool**: Apache Ant (build.xml)
- **IDE**: NetBeans (project structure)

### Design Patterns Implemented

1. **Observer Pattern** - For appointment notifications
   - `AppointmentObserver.java`
   - `AppointmentSubject.java`

2. **Strategy Pattern** - For different request types
   - `DoctorRequestStrategy.java`
   - `ICURequest.java`
   - `LabTestRequest.java`
   - `MedicineRefillRequest.java`

3. **State Pattern** - For ICU room status management
   - `PendingState.java`
   - `ApprovedState.java`
   - `OccupiedState.java`
   - `ReleasedState.java`

4. **Chain of Responsibility Pattern** - For ICU request handling
   - `ICURequestChain.java`
   - `ICURequestHandler.java`
   - `AdminHandler.java`
   - `ReceptionistHandler.java`

5. **Read-Only Interface Pattern** - For prescription access control
   - `IReadOnlyPrescription.java`

### Project Structure

```
HospitalManagmentSystem/
├── HMSClient/              # Client application
│   ├── src/
│   │   ├── client/         # RMI client initialization
│   │   ├── controllers/    # MVC controllers
│   │   ├── gui/            # Swing GUI components
│   │   ├── rmi/            # RMI interface definitions
│   │   └── DesignPattern/ # Client-side design patterns
│   └── build.xml           # Ant build file
│
├── HMSserver/              # Server application
│   ├── src/
│   │   ├── server/         # RMI server and database
│   │   ├── model/          # Domain models
│   │   ├── rmi/            # RMI service implementations
│   │   └── DesignPattern/ # Server-side design patterns
│   └── build.xml           # Ant build file
│
└── MongoDrivers/           # MongoDB Java drivers
    ├── bson-3.12.11.jar
    ├── gson-2.10.jar
    ├── mongodb-driver-3.12.11.jar
    └── mongodb-driver-core-3.12.11.jar
```


## 📝 Database Collections

The system uses the following MongoDB collections:
- `user` - User authentication and role information
- `patient` - Patient records
- `doctor` - Doctor information
- `appointment` - Appointment records
- `prescription` - Prescription data
- `labtest` - Laboratory test records
- `icu` - ICU request records
- `diagnosis` - Diagnosis records
- `drug` - Drug inventory
- `pharmacist` - Pharmacist information
- `refillRequest` - Medicine refill requests
- `admin` - Admin user data
- `receptionist` - Receptionist user data

## 🤝 Contributing

This project was developed as a collaborative effort. Key contributors include:
- Omar Mahmoud
- Salma Mehrez
- Rana Hatem
- Omar Mohammed 
- Tasneem Hatem


