package pl.pja.qrcepta.controlers.doctor;

import static pl.pja.qrcepta.constants.QrceptaConstants.ERROR_MSG_COLOR;
import static pl.pja.qrcepta.constants.QrceptaConstants.MSG_SHOW_TIME_SECONDS;
import static pl.pja.qrcepta.constants.QrceptaConstants.SUCCESS_MSG_COLOR;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.dataConnection.PatientDataSingleton;
import pl.pja.qrcepta.dataConnection.PrescriptionDataSingleton;
import pl.pja.qrcepta.dataConnection.UserDataSingleton;
import pl.pja.qrcepta.model.entity.Patient;
import pl.pja.qrcepta.model.entity.Prescription;
import pl.pja.qrcepta.model.entity.PrescriptionStatus;
import pl.pja.qrcepta.model.entity.User;
import pl.pja.qrcepta.services.PatientService;
import pl.pja.qrcepta.services.PrescriptionService;
import pl.pja.qrcepta.services.QrCodeService;
import pl.pja.qrcepta.services.SecurityCodeGenerator;
import pl.pja.qrcepta.services.implemention.PatientServiceImpl;
import pl.pja.qrcepta.services.implemention.PrescriptionServiceImpl;
import pl.pja.qrcepta.services.implemention.QrCodeServiceImpl;
import pl.pja.qrcepta.services.implemention.SecurityCodeGeneratorImpl;
import pl.pja.qrcepta.utlis.SceneManager;

@Slf4j
public class DoctorControllerTwo implements Initializable {

  @FXML private Button LogOutButton;

  @FXML private Button cancelButton;

  @FXML private TextArea diagnosisTextArea;

  @FXML private Label foundPatientLastName;

  @FXML private Label foundPatientName;

  @FXML private Label infoLabel;

  @FXML private Pane patientPane;

  @FXML private Label peselSearchingInfoPanel;

  @FXML private TextArea prescriptionTextArea;

  @FXML private Button savePrescriptionbutton;

  @FXML private Label usersName;

  private SecurityCodeGenerator securityCodeGenerator = new SecurityCodeGeneratorImpl();
  QrCodeService qrGenerator = new QrCodeServiceImpl();
  private PatientService patientService = new PatientServiceImpl();
  private PrescriptionService prescriptionService = new PrescriptionServiceImpl();

  @FXML
  void goBack(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    SceneManager.changeSceneToDoctorStage(stage);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    User user = UserDataSingleton.getInstance().getUser();
    usersName.setText(user.getUserName());
    showPatientDataPane();
  }

  @FXML
  void logOutAction(ActionEvent event) {
    log.info("LOGING OUT");
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    SceneManager.changeSceneToLoginScene(stage);
    UserDataSingleton.getInstance().setUser(null);
  }

  @FXML
  void savePrescription(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

    if (diagnosisAndPrescriptionIsEmpty()) {
      showInfo("Uzupełnij dane recepty.", ERROR_MSG_COLOR);
      return;
    }
    Patient patient = PatientDataSingleton.getInstance().getPatient();
    Prescription prescription =
        Prescription.builder()
            .recommendation(diagnosisTextArea.getText())
            .medicines(prescriptionTextArea.getText())
            .patient(patient)
            .securityCode(securityCodeGenerator.generateSecurityCode())
            .status(PrescriptionStatus.CREATED)
            .build();
    byte[] qrCodeByteArr = qrGenerator.generateQrPrescription(prescription);
    prescription.setQr_code(qrCodeByteArr);
    prescription = prescriptionService.saveNewPrescription(prescription);
    log.debug("Saved prescription {} for patient {}", prescription, patient);
    PrescriptionDataSingleton.getInstance().setPrescription(prescription);
    showInfo("Recepta zapisana poprawnie", SUCCESS_MSG_COLOR);
    PrescriptionDataSingleton.getInstance().setPrescription(null);
    SceneManager.changeSceneToDoctorStage(stage);
  }

  private void showPatientDataPane() {
    Patient patient = PatientDataSingleton.getInstance().getPatient();
    patientPane.setVisible(true);
    foundPatientName.setText(patient.getName());
    foundPatientLastName.setText(patient.getLastname());
  }

  private void showInfo(String info, Paint color) {
    PauseTransition pause = new PauseTransition(Duration.seconds(MSG_SHOW_TIME_SECONDS));
    pause.setOnFinished(
        e -> {
          infoLabel.setText(info);
          infoLabel.setTextFill(color);
        });
    pause.play();
  }

  private boolean diagnosisAndPrescriptionIsEmpty() {
    return diagnosisTextArea.getText().isEmpty() || prescriptionTextArea.getText().isEmpty();
  }
}
