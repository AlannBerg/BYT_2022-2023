package pl.pja.qrcepta.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.model.entity.Patient;
import pl.pja.qrcepta.model.entity.User;

@Slf4j
public class QrceptaDBConnectionImpl implements QrceptaDBConnection {

  private EntityManager getEntityManager() {
    EntityManagerFactory entityManagerFactory =
        Persistence.createEntityManagerFactory("users-persistence-unit");
    return entityManagerFactory.createEntityManager();
  }

  @Override
  public User getUserFromDatabase(String login, String hashedPassword) {
    log.info("Getting user role form database for {}", login);
    EntityManager entityManager = getEntityManager();
    try {
      User user =
          entityManager
              .createQuery(
                  "select user from User user where user.login = :login and user.hashedPassword = :password",
                  User.class)
              .setParameter( "login",login)
              .setParameter( "password",hashedPassword)
              .getSingleResult();
      return user;
    } catch (Exception e) {
      log.error("Can not get user {} from database {}",login,e.getMessage());
      return null;
    } finally {
      entityManager.close();
    }
  }

  @Override
  public Patient getPatiendByPeselNo(String pesel) {
    //todo
    return null;
  }

  @Override
  public Patient savePatient(Patient patient) {
    //todo sprawdz czy zwraca id
    return null;
  }
}
