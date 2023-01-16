package pl.pja.qrcepta.services;

import pl.pja.qrcepta.model.entity.Prescription;

public interface PrescriptionService {

  void saveNewPrescription(Prescription prescription);
}
