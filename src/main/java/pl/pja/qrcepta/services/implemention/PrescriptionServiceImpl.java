package pl.pja.qrcepta.services.implemention;

import pl.pja.qrcepta.database.QrceptaDBConnection;
import pl.pja.qrcepta.database.QrceptaDBConnectionImpl;
import pl.pja.qrcepta.model.entity.Prescription;
import pl.pja.qrcepta.services.PrescriptionService;

public class PrescriptionServiceImpl implements PrescriptionService {
  private QrceptaDBConnection qrceptaDBConnection = new QrceptaDBConnectionImpl();

  @Override
  public void saveNewPrescription(Prescription prescription) {

  }
}
