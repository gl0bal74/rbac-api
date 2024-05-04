package dev.scottdickerson.rbacservice.rbacapi.initializers;

import com.github.javafaker.Faker;
import dev.scottdickerson.rbacservice.rbacapi.model.AccessTier;
import dev.scottdickerson.rbacservice.rbacapi.model.Credential;
import dev.scottdickerson.rbacservice.rbacapi.model.Intent;
import dev.scottdickerson.rbacservice.rbacapi.model.User;
import dev.scottdickerson.rbacservice.rbacapi.repositories.AccessTierRepository;
import dev.scottdickerson.rbacservice.rbacapi.repositories.CredentialsRepository;
import dev.scottdickerson.rbacservice.rbacapi.repositories.IntentsRepository;
import dev.scottdickerson.rbacservice.rbacapi.repositories.UsersRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

  private final IntentsRepository intentsRepository;
  private final UsersRepository usersRepository;

  private final AccessTierRepository accessTierRepository;
  private final CredentialsRepository credentialsRepository;
  private final Faker faker = new Faker();

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

    List<Intent> intents =
        List.of(
            new Intent("Play music", tier1),
            new Intent("Check weather", tier1),
            new Intent("Check heart rate", tier2),
            new Intent("Book flights", tier3));
    intentsRepository.saveAll(intents);

    List<User> sampleUsers =
        List.of(new User("admin", tier3), new User("friend", tier2), new User("nobody", tier1));
    usersRepository.saveAll(sampleUsers);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void setupCredentials() {
    log.info("Setting up credentials");
    credentialsRepository.deleteAll();
    List<String> intents =
        List.of("Play music", "Check weather", "Check heart rate", "Book flights");
    List<String> users = List.of("admin", "friend", "nobody");
    users.forEach(
        user ->
            intents.forEach(
                intent -> {
                  Credential newCredential =
                      Credential.builder()
                          .apiKey("apiKey")
                          .intentName(intent)
                          .apiKey(UUID.randomUUID().toString())
                          .userName(user)
                          .serviceURL(faker.internet().url())
                          .build();
                  log.info("Saving new Credential {}", newCredential.toString());
                  credentialsRepository.save(newCredential);
                }));
  }
}
