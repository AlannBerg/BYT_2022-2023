package pl.pja.qrcepta.database;

import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.model.entity.Patient;
import pl.pja.qrcepta.model.entity.Prescription;
import pl.pja.qrcepta.model.entity.User;

@Slf4j
public class QrceptaDBConnectionImpl implements QrceptaDBConnection {

  private EntityManager getEntityManager() {
    EntityManagerFactory entityManagerFactory =
        Persistence.createEntityManagerFactory("users-persistence-unit");
    return entityManagerFactory.createEntityManager();
  }

  @Override
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

  @Override
  public Patient getPatiendByPeselNo(@NotNull String pesel) {
    log.info("Geting patient with pesel {} from database", pesel);
    EntityManager entityManager = getEntityManager();
    try {
      return entityManager
          .createNamedQuery("Patient.findByPesel", Patient.class)
          .setParameter("pesel", pesel)
          .getResultList()
          .stream()
          .findFirst()
          .orElse(null);
    } catch (Exception e) {
      log.error("Can not get patient from database {}", e.getMessage());
      return null;
    } finally {
      entityManager.close();
    }
  }

  @Override
  public Patient savePatient(@NotNull Patient patient) {
    log.info("Saving patient {}", patient);
    EntityManager entityManager = getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.persist(patient);
      transaction.commit();
      return patient;
    } catch (Exception e) {
      log.error("Can not save patient {} . {}", patient, e.getMessage());
      transaction.rollback();
      return null;
    } finally {
      entityManager.close();
    }
  }

  @Override
  public Prescription savePrescription(@NotNull Prescription prescription) {
    log.info("Saving prescription {}", prescription);
    EntityManager entityManager = getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.persist(prescription);
      transaction.commit();
      return prescription;
    } catch (Exception e) {
      log.error("Can not save prescription {} {}", prescription, e.getMessage());
      transaction.rollback();
      return null;
    } finally {
      entityManager.close();
    }
  }

  @Override
  public boolean securityCodeExistInDB(@NotNull String securityCode) {
    log.info("Cheching if prescription security code is unique {}", securityCode);
    EntityManager entityManager = getEntityManager();
    try {
      Optional<Prescription> optionalPrescription =
          entityManager
              .createNamedQuery("Prescription.checkIfSecurityCodeIsValid", Prescription.class)
              .setParameter("securityCode", securityCode)
              .getResultList()
              .stream()
              .findFirst();
      Boolean securityCodeExist = optionalPrescription.isPresent();
      log.debug("Prescription security code exist {}", securityCodeExist);
      return securityCodeExist;
    } catch (Exception e) {
      log.error("Can not check if security code is unique {}", e.getMessage());
      throw new RuntimeException("Can not check if security code is unique ");
    } finally {
      entityManager.close();
    }
  }

  @Override
  public Prescription getPrescription(@NotNull Long id) {
    log.info("Geting prescritpion for id {}", id);
    EntityManager entityManager = getEntityManager();
    try {
      return entityManager
          .createNamedQuery("Prescription.getByID", Prescription.class)
          .setParameter("id", id)
          .getResultList()
          .stream()
          .findFirst()
          .orElse(null);
    } catch (Exception e) {
      log.error("Can not find prescritpion for {} {}", id, e.getMessage());
      return null;
    } finally {
      entityManager.close();
    }
  }

  @Override
  public Prescription getPrescription(@NotNull Long id, @NotNull String securityCode) {
    log.info("Geting prescritpion for id {}", id);
    EntityManager entityManager = getEntityManager();
    try {
      return entityManager
          .createNamedQuery("Prescription.getByIDandSecurityCode", Prescription.class)
          .setParameter("id", id)
          .setParameter("securityCode", securityCode)
          .getResultList()
          .stream()
          .findFirst()
          .orElse(null);
    } catch (Exception e) {
      log.error("Can not find prescritpion for {} {}", id, e.getMessage());
      return null;
    } finally {
      entityManager.close();
    }
  }

  @Override
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

  @Override
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

  @Override
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
}
