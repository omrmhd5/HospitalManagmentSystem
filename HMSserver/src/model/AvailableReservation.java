package model;

import java.io.Serializable;

public class AvailableReservation implements Serializable {

    private Doctor doctor;
    private String clinic;
    private String date;
    private String time;

    public AvailableReservation() {}

    public AvailableReservation(Doctor doctor, String clinic, String date, String time) {
        this.doctor = doctor;
        this.clinic = clinic;
        this.date = date;
        this.time = time;
    }

    public Doctor getDoctor() { return doctor; }
    public String getClinic() { return clinic; }
    public String getDate() { return date; }
    public String getTime() { return time; }

    public String toReadableString() {
        return "Doctor: " + doctor.getName() +
               "\nClinic: " + clinic +
               "\nDate: " + date +
               "\nTime: " + time;
    }
}
