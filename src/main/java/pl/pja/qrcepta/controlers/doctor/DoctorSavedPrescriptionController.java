package pl.pja.qrcepta.controlers.doctor;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.dataConnection.DataSingleton;
import pl.pja.qrcepta.model.entity.Patient;
import pl.pja.qrcepta.model.entity.Prescription;
import pl.pja.qrcepta.services.QrCodeService;
import pl.pja.qrcepta.services.implemention.QrCodeServiceImpl;
import pl.pja.qrcepta.utlis.SceneManager;

@Slf4j
public class DoctorSavedPrescriptionController implements Initializable {

  @FXML private Button endButton;

  @FXML private TextArea patientData;

  @FXML private TextArea prescriptionData;

  DataSingleton prescriptionDataSingleton = DataSingleton.getInstance();

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // todo dokoncz jakos ladnie
    Prescription savedPrescription = prescriptionDataSingleton.getPrescription();
    // todo generowanie kodu qr id i security
    Patient savedPatient = savedPrescription.getPatient();
    patientData.setText(savedPatient.toString());
    prescriptionData.setText(savedPrescription.toString());
  }

  @FXML
  void endSavingPrescription(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    log.info("Zakonczenie wystawiania recepty");
    SceneManager.changeSceneToDoctorStage(stage);
  }
}
