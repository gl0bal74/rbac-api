package dev.scottdickerson.rbacservice.rbacapi.initializers;

import dev.scottdickerson.rbacservice.rbacapi.model.AccessTier;
import dev.scottdickerson.rbacservice.rbacapi.model.ProtectedAction;
import dev.scottdickerson.rbacservice.rbacapi.model.User;
import dev.scottdickerson.rbacservice.rbacapi.repositories.AccessTierRepository;
import dev.scottdickerson.rbacservice.rbacapi.repositories.UsersRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {
  private final UsersRepository usersRepository;

  private final AccessTierRepository accessTierRepository;

  @EventListener(ApplicationReadyEvent.class)
  public void setupData() {
    log.info("Setting up initial RBAC data");

    List<User> sampleUsers = List.of(new User("Admin"), new User("Friend"), new User("Nobody"));

    AccessTier tier1 =
        new AccessTier(
            "Tier 1",
            "Lowest level of protection",
            List.of(new ProtectedAction("Play music")),
            sampleUsers);

    AccessTier tier2 =
        new AccessTier(
            "Tier 2",
            "Protected resource access with password",
            List.of(new ProtectedAction("Check heart rate")),
            sampleUsers);

    AccessTier tier3 =
        new AccessTier(
            "Tier 3",
            "Locked down resource access",
            List.of(new ProtectedAction("Book flights"), new ProtectedAction("Send money")),
            sampleUsers);
    List<AccessTier> accessTiers = List.of(tier1, tier2, tier3);

    accessTierRepository.saveAll(accessTiers);
  }
}
