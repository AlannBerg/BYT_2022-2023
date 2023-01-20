package pl.pja.qrcepta;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.pja.qrcepta.utlis.SceneManager;

public class QRceptaApplication extends Application {

  @Override
  public void start(Stage stage) {
    SceneManager.changeSceneToLoginScene(stage);
  }

  public static void main(String[] args) {
    launch();
  }
}
