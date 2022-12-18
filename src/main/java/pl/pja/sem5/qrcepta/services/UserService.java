package pl.pja.sem5.qrcepta.services;

import pl.pja.sem5.qrcepta.model.UserType;

public interface UserService {

  /**
   * Check if such user exist in db and returns it's role.
   * @param login to account
   * @param password to account;
   * @return User type, type of account.
   */
  UserType getUserRole(String login, String password);
}
