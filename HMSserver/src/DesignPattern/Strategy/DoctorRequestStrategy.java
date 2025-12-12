package DesignPattern.Strategy;

import model.Appointment;
import model.Doctor;

public interface DoctorRequestStrategy {
    void executeRequest(Doctor doctor, Appointment appointment);
}
