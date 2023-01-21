package pl.pja.qrcepta.database;

public interface QrceptaDBConnection {
  boolean securityCodeExistInDB(String securityCode);
}
