package model;

import DesignPattern.IReadOnlyPrescription;
import java.io.Serializable;

public class Prescription implements IReadOnlyPrescription, Serializable {

    private int prescriptionID;
    private String medicines;
    private String dosage;
    private String diagnosis;

    public Prescription(int prescriptionID,
                        String medicines,
                        String dosage,
                        String diagnosis) {
        this.prescriptionID = prescriptionID;
        this.medicines = medicines;
        this.dosage = dosage;
        this.diagnosis = diagnosis;
    }

    //  FULL ACCESS (Doctor) 
    public void updateMedicines(String medicines) {
        this.medicines = medicines;
    }

    public void updateDosage(String dosage) {
        this.dosage = dosage;
    }

    public void updateDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    //READ-ONLY ACCESS (Patient)
  
    public String getMedicines() {
        return medicines;
    }

    
    public String getDiagnosis() {
        return diagnosis;
    }
}
