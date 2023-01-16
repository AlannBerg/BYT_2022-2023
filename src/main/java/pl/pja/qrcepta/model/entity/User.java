package pl.pja.qrcepta.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pja.qrcepta.model.UserType;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

  @Id()
  @Column(name = "id")
  private Long id;

  @Column(name = "login")
  private String login;

  @Column(name = "password")
  private String hashedPassword;

  @Enumerated(EnumType.STRING)
  @Column(name = "user_role")
  private UserType userType;

}
