package dev.scottdickerson.rbacservice.rbacapi.model;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "username", nullable = false)
  private String username;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "access_tier_id")
  private AccessTier accessTier;

  public User(String username) {
    this.username = username;
  }
}
