package pl.pja.qrcepta.controlers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import pl.pja.qrcepta.model.UserType;
import pl.pja.qrcepta.services.UserService;
import pl.pja.qrcepta.services.implemention.UserServiceImpl;
import pl.pja.qrcepta.utlis.SceneManager;

@Slf4j
public class LogInController {

  private static final String DOCTOR_SCENE_NAME = "doctor-stage.fxml";
  private static final String DOCTOR_SCENE_TITLE = "QRCEPTA DOCTOR";
  private static final String PHARMACY_SCENE_NAME = "doctor-stage.fxml";
  private static final String PHARMACY_SCENE_TITLE = "QRCEPTA DOCTOR";
  private static final String ADMIN_SCENE_NAME = "admin-stage.fxml";
  private static final String ADMIN_SCENE_TITLE = "QRCEPTA ADMIN";

  @FXML private Button loginButton;

  @FXML private TextField loginLabel;

  @FXML private TextField passwordLabel;

  private UserService userService = new UserServiceImpl();

  @FXML
  void logInAction(ActionEvent event) {
    log.info("Log in action");
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    String securePassword = DigestUtils.sha1Hex(passwordLabel.getText());
    UserType userType = userService.getUserRole(loginLabel.getText(), securePassword);
    try{
      switch (userType) {
        case DOCTOR:
          SceneManager.changeSceneTo(stage, DOCTOR_SCENE_NAME, DOCTOR_SCENE_TITLE);
          break;
        case PHARMACIST:
          SceneManager.changeSceneTo(stage, PHARMACY_SCENE_NAME, PHARMACY_SCENE_TITLE);
          break;
        case ADMIN:
          SceneManager.changeSceneTo(stage, ADMIN_SCENE_NAME, ADMIN_SCENE_TITLE);
        case NOT_EXIST:
          log.error("No such user");
          //todo jakis alert ?
          // todo some error or smth
      }
    }catch (Exception e){
      log.error("Error with setting correct scene for user type {}. {}",userType,e.getMessage());
    }
  }
}
