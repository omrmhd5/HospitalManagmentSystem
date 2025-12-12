package DesignPattern.Strategy;

import model.Doctor;

public interface DoctorRequestStrategy {
    void executeRequest(Doctor doctor);
}
