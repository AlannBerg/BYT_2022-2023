package pl.pja.qrcepta.services.implemention;

import static java.util.Objects.nonNull;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.database.QrceptaDBConnection;
import pl.pja.qrcepta.database.QrceptaDBConnectionImpl;
import pl.pja.qrcepta.model.UserType;
import pl.pja.qrcepta.model.entity.User;
import pl.pja.qrcepta.services.UserService;

@Slf4j
public class UserServiceImpl implements UserService {
  private QrceptaDBConnection qrceptaDBConnection = new QrceptaDBConnectionImpl();

  @Override
  public UserType getUserRole(String login, String hashedPassword) {
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
}
