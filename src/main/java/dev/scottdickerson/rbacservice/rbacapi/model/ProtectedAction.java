package dev.scottdickerson.rbacservice.rbacapi.model;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "protected_action")
@Table(name = "protected_action")
@NoArgsConstructor
@Getter
public class ProtectedAction {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "access_tier_id")
  private AccessTier accessTier;

  public ProtectedAction(String name) {
    this.name = name;
  }
}
