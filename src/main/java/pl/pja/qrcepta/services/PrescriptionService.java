package pl.pja.qrcepta.services;

import java.io.File;
import java.util.List;
import pl.pja.qrcepta.model.entity.Patient;
import pl.pja.qrcepta.model.entity.Prescription;

public interface PrescriptionService {

  Prescription saveNewPrescription(Prescription prescription);

  Prescription getPrescription(String id);

  Prescription getPrescriptionFromQrCode(File file);

  List<Prescription> getPrescriptionsForPatient(Patient foundPatient);

  Prescription setStatusAsRelased(Prescription prescription);

  Prescription setStatusAsCanceled(Prescription choosedPrescription);
}
