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
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.pja.qrcepta.model.UserType;

@Entity
@Data
@Builder
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
  @NamedQuery(
      name = "User.findByLoginAndPassword",
      query =
          "select user from User user where user.login = :login and user.hashedPassword = :password"),
  @NamedQuery(
      name = "User.findByLoginAndName",
      query = "select user from User user where user.login = :login and user.userName = :name")
})
@Table(name = "users")
public class User {

  @Id()
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @NotNull
  @ToString.Include
  @Column(name = "user_name")
  private String userName;

  @ToString.Include
  @Column(name = "login")
  private String login;

  @Column(name = "password")
  private String hashedPassword;

  @Enumerated(EnumType.STRING)
  @ToString.Include
  @Column(name = "user_role")
  private UserType userType;
}
