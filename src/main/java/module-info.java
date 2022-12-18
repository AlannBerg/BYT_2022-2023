module pl.pja.sem5.qrcepta {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.persistence;
  requires org.slf4j;
  requires lombok;

  opens pl.pja.sem5.qrcepta to
      javafx.fxml;

  exports pl.pja.sem5.qrcepta;
  exports pl.pja.sem5.qrcepta.controlers;

  opens pl.pja.sem5.qrcepta.controlers to
      javafx.fxml;

  exports pl.pja.sem5.qrcepta.utlis;

  opens pl.pja.sem5.qrcepta.utlis to
      javafx.fxml;
}
