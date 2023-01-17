package pl.pja.qrcepta.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pja.qrcepta.model.UserType;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
  @NamedQuery(
      name = "User.findByLoginAndPassword",
      query =
          "select user from User user where user.login = :login and user.hashedPassword = :password")
})
@Table(name = "users")
public class User {

  @Id()
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @Column(name = "login")
  private String login;

  @Column(name = "password")
  private String hashedPassword;

  @Enumerated(EnumType.STRING)
  @Column(name = "user_role")
  private UserType userType;
}
