package dev.scottdickerson.rbacservice.rbacapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity(name = "access_tier")
@Table(name = "access_tier")
@Getter
@Setter
@RequiredArgsConstructor
public class AccessTier{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

   @OneToMany(mappedBy = "accessTier", fetch = FetchType.LAZY)
    private Set<ProtectedAction> protectedActions;

}
