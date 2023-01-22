package pl.pja.qrcepta.services.implemention;

import static java.util.Objects.isNull;

import java.io.File;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.database.PrescriptionRepository;
import pl.pja.qrcepta.exceptions.DatabaseSaveException;
import pl.pja.qrcepta.model.dto.PrescriptionDTO;
import pl.pja.qrcepta.model.entity.Patient;
import pl.pja.qrcepta.model.entity.Prescription;
import pl.pja.qrcepta.model.entity.PrescriptionStatus;
import pl.pja.qrcepta.services.PrescriptionService;
import pl.pja.qrcepta.services.QrCodeService;

@Slf4j
public class PrescriptionServiceImpl implements PrescriptionService {
  private PrescriptionRepository prescriptionRepository = new PrescriptionRepository();
  private QrCodeService qrCodeService = new QrCodeServiceImpl();

  @Override
  public Prescription saveNewPrescription(@NotNull Prescription prescription) {
    log.info("Saving prescription");
    Prescription savedPrescription = prescriptionRepository.savePrescription(prescription);
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

    Prescription prescription = prescriptionRepository.getPrescription(idLong);
    if (isNull(prescription)) {
      log.error("Can not find prescription for id {}", id);
      return null;
    }
    log.debug("Prescritpion found for id {} : {}", id, prescription);
    return prescription;
  }

  @Override
  public Prescription getPrescriptionFromQrCode(@NotNull File file) {
    log.info("Geting prescription via qr code");
    PrescriptionDTO prescriptionDTO = qrCodeService.getPrescriptionDtoFromQRCode(file);
    Prescription prescription =
        prescriptionRepository.getPrescription(
            prescriptionDTO.getPatientID(), prescriptionDTO.getSecurityCode());
    if (isNull(prescription)) {
      log.error("Can not find prescription for QR Code");
      return null;
    }
    log.debug("Prescritpion found for  QR Code : {}", prescription);
    return prescription;
  }

  @Override
  public List<Prescription> getPrescriptionsForPatient(@NotNull Patient foundPatient) {
    log.info("Geting prescription list for patient {}", foundPatient);
    List<Prescription> prescriptions =
        prescriptionRepository.getPrescriptionListForPatient(foundPatient.getId());
    log.debug("Found prescriptions {}", prescriptions);
    return prescriptions;
  }

  @Override
  public Prescription setStatusAsRelased(@NotNull Prescription prescription) {
    log.info("Setting prescription {} status as withdrowed", prescription);
    Prescription withdrowedPrescription =
        prescriptionRepository.setNewStatus(prescription.getId(), PrescriptionStatus.RELEASED);
    if (isNull(withdrowedPrescription)) {
      log.error("Error while withdrowing prescritpion");
    }
    return withdrowedPrescription;
  }

  @Override
  public Prescription setStatusAsCanceled(@NotNull Prescription prescription) {
    log.info("Setting prescription {} status as withdrowed", prescription);
    Prescription prescriptionWithNewStatus =
        prescriptionRepository.setNewStatus(prescription.getId(), PrescriptionStatus.CANCELED);
    if (isNull(prescriptionWithNewStatus)) {
      log.error("Error while canceling prescritpion");
    }
    return prescriptionWithNewStatus;
  }
}
