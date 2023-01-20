package pl.pja.qrcepta.services.implemention;

import static pl.pja.qrcepta.constants.QrceptaConstants.SECURITY_CODE_LENGTH;
import static pl.pja.qrcepta.constants.QrceptaConstants.SECURITY_CODE_USE_LETTERS;
import static pl.pja.qrcepta.constants.QrceptaConstants.SECURITY_CODE_USE_NUMBERS;
import static pl.pja.qrcepta.constants.QrceptaConstants.SECURITY_TEXT_TO_GENERATE_NEW_ONE;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import pl.pja.qrcepta.database.QrceptaDBConnection;
import pl.pja.qrcepta.database.QrceptaDBConnectionImpl;
import pl.pja.qrcepta.services.SecurityCodeGenerator;

@Slf4j
public class SecurityCodeGeneratorImpl implements SecurityCodeGenerator {
  private QrceptaDBConnection qrceptaDBConnection = new QrceptaDBConnectionImpl();

  @Override
  public String generateSecurityCode() {
    log.info("Generating random security code");
    String securityCode = SECURITY_TEXT_TO_GENERATE_NEW_ONE;
    while (securityCodeIsNotValid(securityCode)) {
      securityCode =
          RandomStringUtils.random(
              SECURITY_CODE_LENGTH, SECURITY_CODE_USE_LETTERS, SECURITY_CODE_USE_NUMBERS);
    }
    log.debug("Created randomSecurity code {}", securityCode);
    return securityCode;
  }

  private boolean securityCodeIsNotValid(String securityCode) {
    if (Objects.equals(securityCode, SECURITY_TEXT_TO_GENERATE_NEW_ONE)) {
      return true;
    }
    return qrceptaDBConnection.securityCodeExistInDB(securityCode);
  }
}
