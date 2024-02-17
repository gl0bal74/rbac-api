package dev.scottdickerson.rbacservice.rbacapi.initializers;

import dev.scottdickerson.rbacservice.rbacapi.model.AccessTier;
import dev.scottdickerson.rbacservice.rbacapi.model.ProtectedAction;
import dev.scottdickerson.rbacservice.rbacapi.model.User;
import dev.scottdickerson.rbacservice.rbacapi.repositories.AccessTierRepository;
import dev.scottdickerson.rbacservice.rbacapi.repositories.ProtectedActionsRepository;
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

  private final ProtectedActionsRepository protectedActionsRepository;
  private final UsersRepository usersRepository;

  private final AccessTierRepository accessTierRepository;

  @EventListener(ApplicationReadyEvent.class)
  public void setupData() {
    log.info("Setting up initial RBAC data");

    AccessTier tier1 = new AccessTier("Tier 1", "Lowest level of protection", 20);

    AccessTier tier2 =
        new AccessTier("Tier 2", "Protected resource access with password", 50, true, "Tier 2");

    AccessTier tier3 = new AccessTier("Tier 3", "Locked down resource access", 100);

    tier1 = accessTierRepository.save(tier1);
    tier2 = accessTierRepository.save(tier2);
    tier3 = accessTierRepository.save(tier3);

    List<ProtectedAction> protectedActions =
        List.of(
            new ProtectedAction("Play music", tier1),
            new ProtectedAction("Check weather", tier1),
            new ProtectedAction("Check heart rate", tier2),
            new ProtectedAction("Book flights", tier3));
    protectedActionsRepository.saveAll(protectedActions);

    List<User> sampleUsers =
        List.of(new User("admin", tier3), new User("friend", tier2), new User("nobody", tier1));
    usersRepository.saveAll(sampleUsers);
  }
}
