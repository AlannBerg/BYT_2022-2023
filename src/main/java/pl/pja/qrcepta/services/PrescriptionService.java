package pl.pja.qrcepta.services;

import java.io.File;
import pl.pja.qrcepta.model.entity.Prescription;

public interface PrescriptionService {

  Prescription saveNewPrescription(Prescription prescription);

  Prescription getPrescription(String id);

  Prescription getPrescription(String id, String securityCode);

  Prescription getPrescriptionFromQrCode(File file);
}
