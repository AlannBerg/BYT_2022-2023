package pl.pja.qrcepta.utlis;

import javax.validation.constraints.NotNull;
import org.apache.commons.codec.digest.DigestUtils;

public class PasswordUtils {
  public static String encryptPassword(@NotNull String password) {
    return DigestUtils.sha1Hex(password);
  }
}
