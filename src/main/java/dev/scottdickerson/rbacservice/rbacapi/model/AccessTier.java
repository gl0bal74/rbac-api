package dev.scottdickerson.rbacservice.rbacapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AccessTier {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description", nullable = false)
  private String description;

  @Column private long hierarchy;

  private boolean allowSudo;

  @Column(name = "sudoPassword")
  @JsonIgnore
  private String sudoPassword;

  @OneToMany(mappedBy = "accessTier", fetch = FetchType.EAGER)
  @JsonManagedReference
  private List<User> users = new ArrayList<>();

  @OneToMany(mappedBy = "accessTier", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<ProtectedAction> protectedActions = new ArrayList<>();

  public AccessTier(String name, String description, long hierarchy) {
    this.name = name;
    this.description = description;
    this.hierarchy = hierarchy;
  }

  public AccessTier(
      String name, String description, long hierarchy, boolean allowSudo, String sudoPassword) {
    this(name, description, hierarchy);
    this.allowSudo = allowSudo;
    this.sudoPassword = sudoPassword;
  }
}
