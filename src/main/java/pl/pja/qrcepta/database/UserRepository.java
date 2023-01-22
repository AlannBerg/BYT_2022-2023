package pl.pja.qrcepta.database;

import static pl.pja.qrcepta.database.QrceptaDBConnectionImpl.getEntityManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.model.entity.User;

@Slf4j
public class UserRepository {

  public User getUserFromDatabase(@NotNull String login, @NotNull String hashedPassword) {
    log.info("Getting user role form database for {}", login);
    EntityManager entityManager = getEntityManager();
    try {
      return entityManager
          .createNamedQuery("User.findByLoginAndPassword", User.class)
          .setParameter("login", login)
          .setParameter("password", hashedPassword)
          .getResultList()
          .stream()
          .findFirst()
          .orElse(null);
    } catch (Exception e) {
      log.error("Can not get user {} from database {}", login, e.getMessage());
      return null;
    } finally {
      entityManager.close();
    }
  }

  public User saveUser(@NotNull User newUser) {
    EntityManager entityManager = getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.persist(newUser);
      transaction.commit();
      return newUser;
    } catch (Exception e) {
      transaction.rollback();
      log.error("Can not save new user {}", e.getMessage());
      return null;
    } finally {
      entityManager.close();
    }
  }

  public User getByLoginAndName(@NotNull String login, @NotNull String name) {
    EntityManager entityManager = getEntityManager();
    try {
      return entityManager
          .createNamedQuery("User.findByLoginAndName", User.class)
          .setParameter("login", login)
          .setParameter("name", name)
          .getResultList()
          .stream()
          .findFirst()
          .orElse(null);
    } catch (Exception e) {
      log.error("Can not get user by login and name {}", e.getMessage());
      return null;
    } finally {
      entityManager.close();
    }
  }

  public Boolean deleteUser(@NotNull User userToDelete) {
    EntityManager entityManager = getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      User userFromDB =
          entityManager
              .createNamedQuery("User.findByLoginAndName", User.class)
              .setParameter("login", userToDelete.getLogin())
              .setParameter("name", userToDelete.getUserName())
              .getResultList()
              .stream()
              .findFirst()
              .orElse(null);
      entityManager.remove(userFromDB);
      transaction.commit();
      return true;
    } catch (Exception e) {
      transaction.rollback();
      log.error("Can not save new user {}", e.getMessage());
      return false;
    } finally {
      entityManager.close();
    }
  }

  public User changePassword(String login, String newPassword) {
    EntityManager entityManager = getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      User userFromDB =
          entityManager
              .createNamedQuery("User.findByLogin", User.class)
              .setParameter("login", login)
              .getResultList()
              .stream()
              .findFirst()
              .orElse(null);
      userFromDB.setHashedPassword(newPassword);
      entityManager.persist(userFromDB);
      transaction.commit();
      return userFromDB;
    } catch (Exception e) {
      transaction.rollback();
      log.error("Can not save new user {}", e.getMessage());
      return null;
    } finally {
      entityManager.close();
    }
  }
}
