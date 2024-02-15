package dev.scottdickerson.rbacservice.rbacapi.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class AccessTier {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description", nullable = false)
  private String description;

  @OneToMany(mappedBy = "accessTier", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<ProtectedAction> protectedActions = new ArrayList<>();

  @OneToMany(mappedBy = "accessTier", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<User> users = new ArrayList<>();

  public AccessTier(
      String name, String description, List<ProtectedAction> protectedActions, List<User> users) {
    this.name = name;
    this.description = description;
    this.protectedActions = protectedActions;
    this.users = users;
  }
}
