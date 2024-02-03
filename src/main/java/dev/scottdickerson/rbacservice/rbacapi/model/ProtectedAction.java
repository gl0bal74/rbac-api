package dev.scottdickerson.rbacservice.rbacapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity(name = "protected_action")
@Table(name = "protected_action")
@RequiredArgsConstructor
@Getter
public class ProtectedAction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "access_tier_id", nullable = false)
    private AccessTier accessTier;
}
