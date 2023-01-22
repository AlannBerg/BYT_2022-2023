package pl.pja.qrcepta.services;

import pl.pja.qrcepta.model.entity.User;

public interface UserService {

  /**
   * Check if such user exist in db and returns it's role.
   *
   * @param login to account
   * @param password to account;
   * @return User type, type of account.
   */
  User getUser(String login, String password);

  Boolean saveNewUser(User newUser);

  User getByLoginAndName(String text, String text1);

  Boolean deleteUser(User userToDelete);
}
