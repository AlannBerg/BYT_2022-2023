package pl.pja.qrcepta.database;

import static pl.pja.qrcepta.constants.QrceptaConstants.DB_CONFIG_PROPERTY_NAME;
import static pl.pja.qrcepta.constants.QrceptaConstants.PATH_TO_CONFIGURATION_DB_FILE;
import static pl.pja.qrcepta.constants.QrceptaConstants.PATH_TO_SETTINGS_FILE;
import static pl.pja.qrcepta.constants.QrceptaConstants.PERSISTENCE_NAME;
import java.io.FileInputStream;
import java.util.Optional;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.constraints.NotNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import pl.pja.qrcepta.model.entity.Prescription;

@Slf4j
public class QrceptaDBConnectionImpl implements QrceptaDBConnection {

  @SneakyThrows
  public static EntityManager getEntityManager() {
    Properties settings = new Properties();
    try (FileInputStream inputStreamSettings = new FileInputStream(PATH_TO_SETTINGS_FILE)) {
      settings.loadFromXML(inputStreamSettings);
      String configurationPath = settings.getProperty(DB_CONFIG_PROPERTY_NAME);
      Properties properties = new Properties();
      try (FileInputStream inputStreamDatabaseProperties = new FileInputStream(configurationPath)) {
        properties.load(inputStreamDatabaseProperties);
        EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory(PERSISTENCE_NAME, properties);
        return entityManagerFactory.createEntityManager();
      } catch (Exception e) {
        log.error(
            "Blad podczas odczytywania pliku konfiguracyjnego bazy danych {} {}",
            configurationPath,
            e.getMessage());
      }
    } catch (Exception e) {
      log.error(
          "Błąd podczas odczytywania pliku z ustawieniami {} {}",
          PATH_TO_SETTINGS_FILE,
          e.getMessage());
    }
    return null;
  }

//  @SneakyThrows
//  public static EntityManager getEntityManager() {
//    Properties properties = new Properties();
//    try (FileInputStream inputStreamDatabaseProperties =
//        new FileInputStream(PATH_TO_CONFIGURATION_DB_FILE)) {
//      properties.load(inputStreamDatabaseProperties);
//      EntityManagerFactory entityManagerFactory =
//          Persistence.createEntityManagerFactory(PERSISTENCE_NAME, properties);
//      return entityManagerFactory.createEntityManager();
//    } catch (Exception e) {
//      log.error("Can not create persistence e {}", e.getMessage());
//      return null;
//    }
//  }

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
}
