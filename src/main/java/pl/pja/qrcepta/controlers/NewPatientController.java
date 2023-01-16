package pl.pja.qrcepta.controlers;

import static java.util.Objects.isNull;
import static pl.pja.qrcepta.validator.PeselValidator.isPeselValid;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.model.entity.Patient;
import pl.pja.qrcepta.model.entity.Prescription;
import pl.pja.qrcepta.services.PatientService;
import pl.pja.qrcepta.services.PrescriptionService;
import pl.pja.qrcepta.services.implemention.PatientServiceImpl;
import pl.pja.qrcepta.services.implemention.PrescriptionServiceImpl;

@Slf4j
public class NewPatientController {

  private PatientService patientService = new PatientServiceImpl();
  private PrescriptionService prescriptionService = new PrescriptionServiceImpl();

  @FXML private TextField diagnosis;

  @FXML private TextField medicines;

  @FXML private TextField patientLastName;

  @FXML private TextField patientName;

  @FXML private TextField peselNo;

  @FXML private Button setPrescription;

  @FXML
  void setPrescription(ActionEvent event) {
    log.info("Saving prescription for {} {}", patientName.getText(), patientLastName.getText());
    if (!peselIsValid(peselNo.getText())) {
      log.error("Pesel not valid");
    }
    Patient patient = patientService.getPatientWithPesel(peselNo.getText());
    if (isNull(patient)) {
      patient = patientService.saveNewPatient(
          Patient.builder()
              .name(patientName.getText())
              .lastname(patientLastName.getText())
              .peselNo(peselNo.getText())
              .build());
    }
    Prescription prescription = Prescription.builder()
        .patientID(patient.getId())
        .diagnosis(diagnosis.getText())
        .medicines(medicines.getText())
        .build();
    prescriptionService.saveNewPrescription(prescription);
    log.debug("Saved prescription {} for patient {}",prescription,patient);
  }

  private boolean peselIsValid(String pesel) {
    return isPeselValid(pesel);
  }
}
