package dev.scottdickerson.rbacservice.rbacapi.model;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Credential {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  // why isn't this being written in the JSON payload?
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  //  @ManyToOne(fetch = FetchType.LAZY)
  //  @JoinColumn(name = "access_tier_id", nullable = false)
  //  @JsonBackReference
  //  private AccessTier accessTier;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "intent_id", nullable = false)
  private Intent intent;

  private String serviceURL;
  private String apiKey;
}
