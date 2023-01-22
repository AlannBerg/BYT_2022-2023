package pl.pja.qrcepta.controlers.doctor;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static pl.pja.qrcepta.constants.QrceptaConstants.ERROR_MSG_COLOR;
import static pl.pja.qrcepta.constants.QrceptaConstants.MSG_SHOW_TIME_SECONDS;
import static pl.pja.qrcepta.constants.QrceptaConstants.SUCCESS_MSG_COLOR;
import static pl.pja.qrcepta.validator.PeselValidator.isPeselValid;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.dataConnection.PatientDataSingleton;
import pl.pja.qrcepta.dataConnection.UserDataSingleton;
import pl.pja.qrcepta.model.entity.Patient;
import pl.pja.qrcepta.model.entity.Prescription;
import pl.pja.qrcepta.model.entity.User;
import pl.pja.qrcepta.model.entity.UserType;
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
public class DoctorControllerOne implements Initializable {
  @FXML private Button LogOutButton;

  @FXML private Button addNewPatientButton;

  @FXML private Pane addNewPatientPane;

  @FXML private Label foundPatientLastName;

  @FXML private Label foundPatientName;

  @FXML private Button newPatientButton;

  @FXML private Label newPatientInfoLabel;

  @FXML private TextField newPatientLastNameTextField;

  @FXML private TextField newPatientNameTextField;

  @FXML private Pane patientPane;

  @FXML private Pane patientPrescriptionHistoryPane;

  @FXML private TextField peselNo;

  @FXML private Label peselSearchingInfoPanel;

  @FXML private Button setPrescriptionButton;

  @FXML private Label usersName;

  @FXML private ListView<Prescription> patientPrescriptionHistoryListView;

  @FXML private Button cancelAddingNewPatientButton;

  private SecurityCodeGenerator securityCodeGenerator = new SecurityCodeGeneratorImpl();
  QrCodeService qrGenerator = new QrCodeServiceImpl();
  private PatientService patientService = new PatientServiceImpl();
  private PrescriptionService prescriptionService = new PrescriptionServiceImpl();

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    User user = UserDataSingleton.getInstance().getUser();
    usersName.setText(user.getUserName());
  }

  @FXML
  void logOutAction(ActionEvent event) {
    log.info("LOGING OUT");
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    SceneManager.changeSceneToLoginScene(stage);
    UserDataSingleton.getInstance().setUser(null);
  }

  @FXML
  void searchPatient(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    if (!peselIsValid(peselNo.getText())) {
      showPeselLabelInfo("Niepoprawny pesel", ERROR_MSG_COLOR);
      return;
    }
    Patient patient = patientService.getPatientWithPesel(peselNo.getText());

    if (isNull(patient)) {
      showNewPatientPane();
      return;
    }
    PatientDataSingleton.getInstance().setPatient(patient);
    showPatientDataPane();
    showPatientPresciptionHistory();
    setPrescriptionButton.setDisable(false);
  }

  @FXML
  void addNewButton(ActionEvent event) {
    if (nonNull(PatientDataSingleton.getInstance().getPatient())) {
      showPatientDataPane();
    }

    if (newPatientNameTextField.getText().isEmpty()
        || newPatientLastNameTextField.getText().isEmpty()) {
      newPatientShowMesseage("Uzupełnij dane nowego pacjenta");
      newPatientNameTextField.clear();
      newPatientLastNameTextField.clear();
    }
    Patient patient =
        patientService.saveNewPatient(
            Patient.builder()
                .name(newPatientNameTextField.getText())
                .lastname(newPatientLastNameTextField.getText())
                .peselNo(peselNo.getText())
                .role(UserType.PATIENT)
                .build());
    if (isNull(patient)) {
      newPatientShowMesseage("Błąd podczas zapisywanai nowego pacjenta");
      return;
    }
    showPeselLabelInfo("Nowy pacjent dodany ponownie", SUCCESS_MSG_COLOR);
    PatientDataSingleton.getInstance().setPatient(patient);
    showPatientDataPane();
    setPrescriptionButton.setDisable(false);
    addNewPatientPane.setVisible(false);
  }

  @FXML
  void setPrescription(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    SceneManager.changeSceneAddPrescriptionForPatient(stage);
  }

  @FXML
  void cancelAddingNewPatient(ActionEvent event) {
    newPatientNameTextField.clear();
    newPatientLastNameTextField.clear();
    addNewPatientPane.setVisible(false);
  }

  // todo
  private boolean peselIsValid(String pesel) {
    return isPeselValid(pesel);
  }

  private void showNewPatientPane() {
    addNewPatientPane.setVisible(true);
  }

  private void newPatientShowMesseage(String info) {
    PauseTransition pause = new PauseTransition(Duration.seconds(MSG_SHOW_TIME_SECONDS));
    pause.setOnFinished(
        e -> {
          newPatientInfoLabel.setText(info);
          newPatientInfoLabel.setTextFill(ERROR_MSG_COLOR);
        });
    pause.play();
  }

  private void showPatientDataPane() {
    Patient foundPatient = PatientDataSingleton.getInstance().getPatient();
    patientPane.setVisible(true);
    foundPatientName.setText(foundPatient.getName());
    foundPatientLastName.setText(foundPatient.getLastname());
  }

  private void showPatientPresciptionHistory() {
    Patient foundPatient = PatientDataSingleton.getInstance().getPatient();
    List<Prescription> prescriptionsForPatient =
        prescriptionService.getPrescriptionsForPatient(foundPatient);
    if (isNotEmpty(prescriptionsForPatient)) {
      patientPrescriptionHistoryPane.setVisible(true);
      patientPrescriptionHistoryListView.getItems().addAll(prescriptionsForPatient);
    }
  }

  // todo do jakichs utilsow
  private void showPeselLabelInfo(String info, Paint color) {
    PauseTransition pause = new PauseTransition(Duration.seconds(MSG_SHOW_TIME_SECONDS));
    pause.setOnFinished(
        e -> {
          peselSearchingInfoPanel.setText(info);
          peselSearchingInfoPanel.setTextFill(color);
        });
    pause.play();
  }
}
