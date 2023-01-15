package pl.pja.qrcepta.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import pl.pja.qrcepta.model.UserType;

@Entity
@Data
@Table(name = "users")
public class User {

  @Id()
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "login")
  private String login;

  @Column(name = "password")
  private String hashedPassword;

//  @Enumerated(EnumType.STRING)
//  @Column(name = "user_role")
//  private UserType userType;
}
