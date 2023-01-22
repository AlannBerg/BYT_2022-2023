package pl.pja.qrcepta.services.implemention;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.database.UserRepository;
import pl.pja.qrcepta.model.entity.User;
import pl.pja.qrcepta.services.UserService;

@Slf4j
public class UserServiceImpl implements UserService {
  private UserRepository userRepository = new UserRepository();

  @Override
  public User getUser(@NotNull String login, @NotNull String hashedPassword) {
    log.info("Getting  user role, checking if it exist.");
    User user = userRepository.getUserFromDatabase(login, hashedPassword);
    if (nonNull(user)) {
      log.debug("User returned  is {}", user);
      return user;
    }
    log.error("User {} dont exist", login);
    return null;
  }

  @Override
  public Boolean saveNewUser(@NotNull User newUser) {
    log.info("Saving new user.");
    User savedUser = userRepository.saveUser(newUser);
    Boolean savingUserWasSuccessful = nonNull(savedUser);
    log.debug("Saving new user was successful {}", savingUserWasSuccessful);
    return savingUserWasSuccessful;
  }

  @Override
  public User getByLoginAndName(@NotNull String login, @NotNull String name) {
    log.info("Get user by login {} and name {}", login, name);
    User user = userRepository.getByLoginAndName(login, name);
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
    Boolean deleteWasSuccessful = userRepository.deleteUser(userToDelete);
    log.debug("Deleting user {} was successful {}", userToDelete, deleteWasSuccessful);
    return deleteWasSuccessful;
  }

  @Override
  public User changePassword(@NotNull String login, @NotNull String newPassword) {
    log.info("Changing pasword for user {} ", login);
    User user = userRepository.changePassword(login, newPassword);
    if (isNull(user)) {
      log.debug("Can not update password for {}", login);
    }
    return user;
  }
}
