package dev.scottdickerson.rbacservice.rbacapi.api;

import dev.scottdickerson.rbacservice.rbacapi.model.AccessTier;
import dev.scottdickerson.rbacservice.rbacapi.model.Intent;
import dev.scottdickerson.rbacservice.rbacapi.model.User;
import dev.scottdickerson.rbacservice.rbacapi.model.requests.ActionAccessCheckRequest;
import dev.scottdickerson.rbacservice.rbacapi.model.responses.ActionAccessCheckResponse;
import dev.scottdickerson.rbacservice.rbacapi.repositories.AccessTierRepository;
import dev.scottdickerson.rbacservice.rbacapi.repositories.IntentsRepository;
import dev.scottdickerson.rbacservice.rbacapi.repositories.UsersRepository;
import dev.scottdickerson.rbacservice.rbacapi.services.IntentsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class APIController {
  private final AccessTierRepository accessTierRepository;
  private final IntentsRepository intentsRepository;
  private final IntentsService intentsService;
  private final UsersRepository usersRepository;

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
    return ResponseEntity.ok(accessTierRepository.findByName(name));
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
}
