package pl.pja.qrcepta.controlers;

import static pl.pja.qrcepta.utlis.SceneConstants.ERROR_MSG_TEXT_CLEAR;
import static pl.pja.qrcepta.utlis.SceneConstants.ERROR_MSG_SHOW_TIME_SECONDS;
import static pl.pja.qrcepta.utlis.SceneConstants.LOG_IN_ERROR_MSG_TEXT;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import pl.pja.qrcepta.model.UserType;
import pl.pja.qrcepta.services.UserService;
import pl.pja.qrcepta.services.implemention.UserServiceImpl;
import pl.pja.qrcepta.utlis.SceneManager;

@Slf4j
public class LogInController {

  @FXML private Button loginButton;

  @FXML private TextField loginLabel;

  @FXML private TextField passwordLabel;

  @FXML private Text loginErrorMsgField;

  private UserService userService = new UserServiceImpl();

  @FXML
  void logInAction(ActionEvent event) {
    log.info("Log in action");
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    String securePassword = DigestUtils.sha1Hex(passwordLabel.getText());
    UserType userType = userService.getUserRole(loginLabel.getText(), securePassword);
    try {
      switch (userType) {
        case DOCTOR:
          SceneManager.changeSceneToDoctorStage(stage);
          break;
        case PHARMACIST:
          SceneManager.changeSceneToPharmacyStage(stage);
          break;
        case ADMIN:
          SceneManager.changeSceneToAdminPanel(stage);
        case NOT_EXIST:
          showErrorMessage();
      }
    } catch (Exception e) {
      log.error("Error with setting correct scene for user type {}. {}", userType, e.getMessage());
    }
  }

  private void showErrorMessage() {
    log.error("User not found");
    loginLabel.clear();
    passwordLabel.clear();
    loginErrorMsgField.setText(LOG_IN_ERROR_MSG_TEXT);
    PauseTransition pause = new PauseTransition(Duration.seconds(ERROR_MSG_SHOW_TIME_SECONDS));
    pause.setOnFinished(e -> loginErrorMsgField.setText(ERROR_MSG_TEXT_CLEAR));
    pause.play();
  }
}
