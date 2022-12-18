package pl.pja.sem5.qrcepta.controlers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import pl.pja.sem5.qrcepta.utlis.SceneManager;
import pl.pja.sem5.qrcepta.model.UserType;
import pl.pja.sem5.qrcepta.services.UserService;
import pl.pja.sem5.qrcepta.services.UserServiceImpl;

@Slf4j
public class LogInController {

  private static final String DOCTOR_SCENE_NAME = "doctor-stage.fxlm";
  private static final String DOCTOR_SCENE_TITLE = "QRCEPTA DOCTOR";
  private static final String PHARMACY_SCENE_NAME = "doctor-stage.fxlm";
  private static final String PHARMACY_SCENE_TITLE = "QRCEPTA DOCTOR";

  @FXML private Button loginButton;

  @FXML private TextField loginLabel;

  @FXML private TextField passwordLabel;

  private UserService userService = new UserServiceImpl();

  @FXML
  void logInAction(ActionEvent event) {
    log.info("Log in action");
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    UserType userType = userService.getUserRole(loginLabel.getText(), passwordLabel.getText());
    switch (userType) {
      case DOCTOR:
        stage.setMaximized(true);
        SceneManager.changeSceneTo(stage, DOCTOR_SCENE_NAME, DOCTOR_SCENE_TITLE);
        break;
      case PHARMACIST:
        stage.setMaximized(true);
        SceneManager.changeSceneTo(stage, PHARMACY_SCENE_NAME, PHARMACY_SCENE_TITLE);
        break;
      case NOT_EXIST:
        log.error("No such user");
        // todo some error or smth
    }
  }
}
