package pl.pja.qrcepta.model;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public enum UserType {
  DOCTOR("DOCTOR"),
  PHARMACIST("PHARMACIST"),
  NOT_EXIST("NOT_EXIST");

  private final String name;

  public static UserType of(String name) {
    try {
      return UserType.valueOf(UserType.class, name);
    } catch (IllegalArgumentException e) {
      log.error("Can not parse string {} to Import Type. {}", name, e.getMessage());
      throw new EnumConstantNotPresentException(UserType.class, name);
    }
  }
}
