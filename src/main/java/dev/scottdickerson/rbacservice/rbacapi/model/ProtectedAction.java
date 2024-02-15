package dev.scottdickerson.rbacservice.rbacapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProtectedAction {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String name;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "access_tier_id", nullable = false)
  @JsonBackReference
  private AccessTier accessTier;

  public ProtectedAction(String name) {
    this.name = name;
  }
  public ProtectedAction(String name, AccessTier accessTier) {
    this.name = name;
    this.accessTier = accessTier;
  }
}
