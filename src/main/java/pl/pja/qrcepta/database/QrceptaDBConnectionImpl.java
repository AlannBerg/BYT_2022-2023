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
      Optional<User> optionalUser =
          entityManager
              .createNamedQuery("User.findByLoginAndPassword", User.class)
              .setParameter("login", login)
              .setParameter("password", hashedPassword)
              .getResultList()
              .stream()
              .findFirst();
      if (!optionalUser.isPresent()) {
        log.debug("No user  {} find in database.", login);
        return null;
      }
      return optionalUser.get();
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
      Optional<Patient> optionalPatient =
          entityManager
              .createNamedQuery("Patient.findByPesel", Patient.class)
              .setParameter("pesel", pesel)
              .getResultList()
              .stream()
              .findFirst();
      if (!optionalPatient.isPresent()) {
        log.debug("No patient found in database.");
        return null;
      }
      return optionalPatient.get();
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
  public Prescription getPrescription(Long id) {
    log.info("Geting prescritpion for id {}", id);
    EntityManager entityManager = getEntityManager();
    try {
      Optional<Prescription> optionalPrescription =
          entityManager
              .createNamedQuery("Prescription.getByID", Prescription.class)
              .setParameter("id", id)
              .getResultList()
              .stream()
              .findFirst();
      if (!optionalPrescription.isPresent()) {
        log.debug("Prescritpion for id {} not found ", id);
        return null;
      }
      return optionalPrescription.get();
    } catch (Exception e) {
      log.error("Can not find prescritpion for {} {}", id, e.getMessage());
      return null;
    } finally {
      entityManager.close();
    }
  }

  @Override
  public Prescription getPrescription(@NotNull String id, @NotNull String securityCode) {
    log.info("Geting prescritpion for id {}", id);
    EntityManager entityManager = getEntityManager();
    try {
      Optional<Prescription> optionalPrescription =
          entityManager
              .createNamedQuery("Prescription.getByIDandSecurityCode", Prescription.class)
              .setParameter("id", id)
              .setParameter("securityCode", securityCode)
              .getResultList()
              .stream()
              .findFirst();
      if (!optionalPrescription.isPresent()) {
        log.debug("Prescritpion for id {} not found ", id);
        return null;
      }
      return optionalPrescription.get();
    } catch (Exception e) {
      log.error("Can not find prescritpion for {} {}", id, e.getMessage());
      return null;
    } finally {
      entityManager.close();
    }
  }
}
