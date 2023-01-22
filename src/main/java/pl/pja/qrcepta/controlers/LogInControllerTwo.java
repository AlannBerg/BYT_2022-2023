package pl.pja.qrcepta.controlers;

import static java.util.Objects.isNull;
import static pl.pja.qrcepta.constants.QrceptaConstants.ERROR_MSG_COLOR;
import static pl.pja.qrcepta.constants.QrceptaConstants.MSG_SHOW_TIME_SECONDS;
import static pl.pja.qrcepta.constants.QrceptaConstants.SUCCESS_MSG_COLOR;

import java.util.Objects;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.model.entity.User;
import pl.pja.qrcepta.services.UserService;
import pl.pja.qrcepta.services.implemention.UserServiceImpl;
import pl.pja.qrcepta.utlis.PasswordUtils;
import pl.pja.qrcepta.utlis.SceneManager;

@Slf4j
public class LogInControllerTwo {

  @FXML private Button changePasswordButton;

  @FXML private Button goBackButton;

  @FXML private TextField loginTextField;

  @FXML private Label infoLabel;

  @FXML private PasswordField newPassword;

  @FXML private PasswordField newPasswordCopy;

  private UserService userService = new UserServiceImpl();

  @FXML
  void changePassword(ActionEvent event) {
    if (oneFieldIsEmpty()) {
      showInfo("Uzupełnij wymagane pola", ERROR_MSG_COLOR);
      return;
    }
    if (!Objects.equals(newPassword.getText(), newPasswordCopy.getText())) {
      showInfo("Podane hasła różnią się ", ERROR_MSG_COLOR);
      return;
    }
    String hashedPassword = PasswordUtils.encryptPassword(newPassword.getText());
    User user = userService.changePassword(loginTextField.getText(), hashedPassword);
    if (isNull(user)) {
      showInfo("Błąd podczas zmiany hasła", ERROR_MSG_COLOR);
      return;
    }
    showInfo("Zmiana hasła zakonczona.", SUCCESS_MSG_COLOR);
  }

  @FXML
  void goBackButton(ActionEvent event) {
    log.info("LOGING OUT");
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    SceneManager.changeSceneToLoginScene(stage);
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

  private boolean oneFieldIsEmpty() {
    return loginTextField.getText().isEmpty()
        || newPassword.getText().isEmpty()
        || newPasswordCopy.getText().isEmpty();
  }
}
