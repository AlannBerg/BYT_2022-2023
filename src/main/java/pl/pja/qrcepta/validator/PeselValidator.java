package pl.pja.qrcepta.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class PeselValidator {
  public static boolean isPeselValid(String peselNumber) {
    return StringUtils.isNumeric(peselNumber);
  }
}
