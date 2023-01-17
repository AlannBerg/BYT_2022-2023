package pl.pja.qrcepta.services.implemention;

import static java.util.Objects.isNull;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.database.QrceptaDBConnection;
import pl.pja.qrcepta.database.QrceptaDBConnectionImpl;
import pl.pja.qrcepta.exceptions.DatabaseSaveException;
import pl.pja.qrcepta.model.entity.Prescription;
import pl.pja.qrcepta.services.PrescriptionService;

@Slf4j
public class PrescriptionServiceImpl implements PrescriptionService {
  private QrceptaDBConnection qrceptaDBConnection = new QrceptaDBConnectionImpl();

  @Override
  public Prescription saveNewPrescription(@NotNull Prescription prescription) {
    log.info("Saving prescription");
    Prescription savedPrescription = qrceptaDBConnection.savePrescription(prescription);
    if (isNull(savedPrescription)) {
      log.error("Can not save prescription {}", prescription);
      throw new DatabaseSaveException("Can not save prescritpion");
    }
    return savedPrescription;
  }

  @Override
  public Prescription getPrescription(String id) {
    log.info("Geting prescription for id {}", id);
    Long idLong;
    try {
      idLong = Long.valueOf(id);
    } catch (Exception e) {
      log.error("Can not parse id to number {} ", id);
      return null;
    }

    Prescription prescription = qrceptaDBConnection.getPrescription(idLong);
    if (isNull(prescription)) {
      log.error("Can not find prescription for id {}", id);
      return null;
    }
    log.debug("Prescritpion found for id {} : {}", id, prescription);
    return prescription;
  }

  @Override
  public Prescription getPrescription(String id, String securityCode) {
    log.info("Geting prescription for id {}", id);
    Prescription prescription = qrceptaDBConnection.getPrescription(id, securityCode);
    if (isNull(prescription)) {
      log.error("Can not find prescription for id {}", id);
      return null;
    }
    log.debug("Prescritpion found for id {} : {}", id, prescription);
    return prescription;
  }
}
