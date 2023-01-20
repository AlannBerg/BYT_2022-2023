package pl.pja.qrcepta.services.implemention;

import static java.util.Objects.nonNull;

import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.database.QrceptaDBConnection;
import pl.pja.qrcepta.database.QrceptaDBConnectionImpl;
import pl.pja.qrcepta.model.entity.UserType;
import pl.pja.qrcepta.model.entity.User;
import pl.pja.qrcepta.services.UserService;

@Slf4j
public class UserServiceImpl implements UserService {
  private QrceptaDBConnection qrceptaDBConnection = new QrceptaDBConnectionImpl();

  @Override
  public UserType getUserRole(@NotNull String login, @NotNull String hashedPassword) {
    log.info("Getting  user role, checking if it exist.");
    User user = qrceptaDBConnection.getUserFromDatabase(login, hashedPassword);
    if (nonNull(user)) {
      UserType userType = user.getUserType();
      log.debug("User {} returned role is {}", login, userType);
      return userType;
    }
    log.error("User {} dont exist", login);
    return UserType.NOT_EXIST;
  }

  @Override
  public Boolean saveNewUser(@NotNull User newUser) {
    log.info("Saving new user.");
    User savedUser = qrceptaDBConnection.saveUser(newUser);
    Boolean savingUserWasSuccessful = nonNull(savedUser);
    log.debug("Saving new user was successful {}", savingUserWasSuccessful);
    return savingUserWasSuccessful;
  }

  @Override
  public User getByLoginAndName(@NotNull String login, @NotNull String name) {
    log.info("Get user by login {} and name {}", login, name);
    User user = qrceptaDBConnection.getByLoginAndName(login, name);
    if (nonNull(user)) {
      log.debug("Found user {} for login {} and name {}", user, login, name);
      return user;
    }
    log.error("User {} {} dont exist", login, name);
    return null;
  }

  @Override
  public Boolean deleteUser(@NotNull User userToDelete) {
    log.info("Deleting user {}", userToDelete);
    Boolean deleteWasSuccessful = qrceptaDBConnection.deleteUser(userToDelete);
    log.debug("Deleting user {} was successful {}", userToDelete, deleteWasSuccessful);
    return deleteWasSuccessful;
  }
}
