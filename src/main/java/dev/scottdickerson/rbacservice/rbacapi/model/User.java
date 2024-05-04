package dev.scottdickerson.rbacservice.rbacapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "username", nullable = false)
  private String username;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "access_tier_id", nullable = false)
  @JsonBackReference
  private AccessTier accessTier;

  //  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  //  @JoinColumn(name = "credential_id", nullable = false)
  //  @JsonBackReference
  //  private Credential credential;

  public User(String username, AccessTier accessTier) {
    this.username = username;
    this.accessTier = accessTier;
  }
}
