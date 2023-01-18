package pl.pja.qrcepta.database;

import pl.pja.qrcepta.model.entity.Patient;
import pl.pja.qrcepta.model.entity.Prescription;
import pl.pja.qrcepta.model.entity.User;

public interface QrceptaDBConnection {

  // todo
  User getUserFromDatabase(String login, String password);

  Patient getPatiendByPeselNo(String pesel);

  Patient savePatient(Patient patient);

  Prescription savePrescription(Prescription prescription);

  boolean securityCodeExistInDB(String securityCode);

  Prescription getPrescription(Long id);

  // todo
  Prescription getPrescription(String id, String securityCode);

  User saveUser(User newUser);

  User getByLoginAndName(String login, String name);

  Boolean deleteUser(User userToDelete);
}
