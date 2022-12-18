package pl.pja.sem5.qrcepta;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import pl.pja.sem5.qrcepta.utlis.SceneManager;

public class QRceptaApplication extends Application {

  private static final String STAGE_TITLE = "QRCEPTA LOGOWANIE";
  private static final String LOG_IN_SCENE_NAME = "logowanie.fxml";
  private static final double STAGE_WIDTH = 750;
  private static final double STAGE_HEIGHT = 500;

  @Override
  public void start(Stage stage) throws IOException {
    SceneManager.changeSceneTo(stage, LOG_IN_SCENE_NAME, STAGE_TITLE, STAGE_WIDTH, STAGE_HEIGHT);
  }

  public static void main(String[] args) {
    launch();
  }
}
