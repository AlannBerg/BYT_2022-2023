package pl.pja.qrcepta.database;

import pl.pja.qrcepta.model.entity.Patient;
import pl.pja.qrcepta.model.entity.User;

public interface QrceptaDBConnection {

  //todo
  User getUserFromDatabase(String login, String password);

  Patient getPatiendByPeselNo(String pesel);

  Patient savePatient(Patient patient);
}
