module pl.pja.qrcepta {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.persistence;
  requires org.slf4j;
  requires lombok;
  requires org.apache.commons.codec;
  requires java.sql;
  requires org.hibernate.orm.core;
  requires java.validation;
  requires org.apache.commons.lang3;

  opens pl.pja.qrcepta to
      javafx.fxml;

  exports pl.pja.qrcepta;
  exports pl.pja.qrcepta.controlers;
  exports pl.pja.qrcepta.model.entity;

  opens pl.pja.qrcepta.model.entity to
      javafx.fxml,
      org.hibernate.orm.core;
  opens pl.pja.qrcepta.controlers to
      javafx.fxml;

  exports pl.pja.qrcepta.utlis;

  opens pl.pja.qrcepta.utlis to
      javafx.fxml;

  exports pl.pja.qrcepta.controlers.doctor;

  opens pl.pja.qrcepta.controlers.doctor to
      javafx.fxml;
  opens pl.pja.qrcepta.controlers.admin to
      javafx.fxml;

  exports pl.pja.qrcepta.controlers.pharmacy;

  opens pl.pja.qrcepta.controlers.pharmacy to
      javafx.fxml;
}
