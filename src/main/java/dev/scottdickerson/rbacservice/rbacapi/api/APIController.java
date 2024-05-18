package dev.scottdickerson.rbacservice.rbacapi.api;

import dev.scottdickerson.rbacservice.rbacapi.model.AccessTier;
import dev.scottdickerson.rbacservice.rbacapi.model.Credential;
import dev.scottdickerson.rbacservice.rbacapi.model.Intent;
import dev.scottdickerson.rbacservice.rbacapi.model.User;
import dev.scottdickerson.rbacservice.rbacapi.model.requests.ActionAccessCheckRequest;
import dev.scottdickerson.rbacservice.rbacapi.model.responses.ActionAccessCheckResponse;
import dev.scottdickerson.rbacservice.rbacapi.repositories.AccessTierRepository;
import dev.scottdickerson.rbacservice.rbacapi.repositories.CredentialsRepository;
import dev.scottdickerson.rbacservice.rbacapi.repositories.IntentsRepository;
import dev.scottdickerson.rbacservice.rbacapi.repositories.UsersRepository;
import dev.scottdickerson.rbacservice.rbacapi.services.ActionService;
import dev.scottdickerson.rbacservice.rbacapi.services.IntentsService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class APIController {
  private final AccessTierRepository accessTierRepository;
  private final IntentsRepository intentsRepository;
  private final IntentsService intentsService;
  private final UsersRepository usersRepository;
  private final CredentialsRepository credentialsRepository;
  private final ActionService actionService;

  @GetMapping("/access-tiers")
  public ResponseEntity<List<AccessTier>> getAccessTiers() {
    return ResponseEntity.ok(accessTierRepository.findAll());
  }

  @PostMapping("/access-tiers")
  public ResponseEntity<AccessTier> createAccessTier(@RequestBody AccessTier accessTier) {
    log.info("Creating access tier: " + accessTier);
    return ResponseEntity.ok(accessTierRepository.save(accessTier));
  }

  @GetMapping("/access-tiers/{name}")
  public ResponseEntity<AccessTier> getAccessTier(@PathVariable("name") String name) {
    AccessTier accessTier = accessTierRepository.findByName(name);
    return ResponseEntity.ok(accessTier);
  }

  @GetMapping("/access-tiers/user/{userId}")
  public ResponseEntity<AccessTier> getAccessTierByUserId(
      @PathVariable(name = "userId") UUID userId) {
    User user = usersRepository.findById(userId).orElseThrow();
    AccessTier accessTier = accessTierRepository.findByUsers(user);
    if (accessTier == null) {
      log.info("Access tier not found for user id: " + userId);
      return ResponseEntity.status(404).build();
    }
    log.info("Access tier for user: " + accessTier.getName());
    //    return ResponseEntity.status(200).build();
    return ResponseEntity.ok(accessTier);
  }

  @GetMapping("/access-tiers/intent/{intentId}")
  public ResponseEntity<AccessTier> getAccessTierByIntentId(
      @PathVariable(name = "intentId") UUID intentId) {
    Intent intent = intentsRepository.findById(intentId).orElseThrow();
    AccessTier accessTier = accessTierRepository.findByIntents(intent);
    if (accessTier == null) {
      log.info("Access tier not found for intent id: " + intentId );
      return ResponseEntity.status(404).build();
    }
    log.info("Access tier for intent id" + intentId + ": " + accessTier.getName());
    //    return ResponseEntity.status(200).build();
    return ResponseEntity.ok(accessTier);
  }

  @GetMapping("/access-tiers/intent/{intentId}/user/{userId}")
  public ResponseEntity<AccessTier> getAccessTierByIntentIdAndUserId(
      @PathVariable(name = "intentId") UUID intentId, @PathVariable(name = "userId") UUID userId) {
    Intent intent = intentsRepository.findById(intentId).orElseThrow();
    User user = usersRepository.findById(userId).orElseThrow();

    AccessTier accessTier = accessTierRepository.findByIntentsAndUsers(intent, user);
    if (accessTier == null) {
        log.info("Access tier not found for intent id: " + intentId + " and user id: " + userId);
        return ResponseEntity.status(404).build();
    }
    log.info("Access tier for user: " + accessTier.getName());
    //    return ResponseEntity.status(200).build();
    return ResponseEntity.ok(accessTier);
  }

  @GetMapping("/intents")
  public ResponseEntity<List<Intent>> getIntents() {
    return ResponseEntity.ok(intentsRepository.findAll());
  }

  @PostMapping("/intents")
  public ResponseEntity<Intent> createIntent(@RequestBody Intent intent) {
    log.info("Creating protected action: " + intent);
    return ResponseEntity.ok(intentsRepository.save(intent));
  }

  @GetMapping("/users")
  public ResponseEntity<List<User>> getUsers() {
    List<User> users = usersRepository.findAll();
    users.stream().map(User::getUsername).forEach(log::info);
    return ResponseEntity.ok(usersRepository.findAll());
  }

  @PostMapping("/users")
  public ResponseEntity<User> createUser(@RequestBody User user) {
    log.info("Creating user: " + user);
    return ResponseEntity.ok(usersRepository.save(user));
  }

  @PostMapping(value = "/check-permission")
  public ResponseEntity<ActionAccessCheckResponse> doesUserHavePermission(
      @RequestBody ActionAccessCheckRequest actionAccessCheckRequest) {
    return intentsService.checkPermission(actionAccessCheckRequest);
  }

  @GetMapping("/credentials")
  public ResponseEntity<List<Credential>> getCredentials() {
    return ResponseEntity.ok(credentialsRepository.findAll());
  }

  @PostMapping("/action/{intentId}/user/{userId}")
  public ResponseEntity<String> performIntent(
      @PathVariable UUID intentId, @PathVariable UUID userId) {
    return actionService.performAction(intentId, userId);
  }
}
