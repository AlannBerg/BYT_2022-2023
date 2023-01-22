package pl.pja.qrcepta.model.entity;

public enum PrescriptionStatus {
  CREATED("Utworzona"),
  RELEASED("Wydana"),
  CANCELED("Zarchiwizowana");

  public String statusName;

  PrescriptionStatus(String status) {
    this.statusName = status;
  }
}
