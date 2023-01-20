package pl.pja.qrcepta.services;

import pl.pja.qrcepta.model.entity.Patient;

public interface PatientService {

  Patient getPatientWithPesel(String text);

  Patient saveNewPatient(Patient build);
}
