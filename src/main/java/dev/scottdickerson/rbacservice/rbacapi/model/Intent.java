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
public class Intent {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "access_tier_id", nullable = false)
  @JsonBackReference
  private AccessTier accessTier;

  public Intent(String name) {
    this.name = name;
  }

  public Intent(String name, AccessTier accessTier) {
    this.name = name;
    this.accessTier = accessTier;
  }
}
