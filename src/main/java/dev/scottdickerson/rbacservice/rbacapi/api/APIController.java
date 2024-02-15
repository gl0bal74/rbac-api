package dev.scottdickerson.rbacservice.rbacapi.api;

import dev.scottdickerson.rbacservice.rbacapi.model.AccessTier;
import dev.scottdickerson.rbacservice.rbacapi.model.responses.ActionAccessCheckResponse;
import dev.scottdickerson.rbacservice.rbacapi.model.ProtectedAction;
import dev.scottdickerson.rbacservice.rbacapi.model.User;
import dev.scottdickerson.rbacservice.rbacapi.repositories.AccessTierRepository;
import dev.scottdickerson.rbacservice.rbacapi.repositories.ProtectedActionsRepository;
import dev.scottdickerson.rbacservice.rbacapi.repositories.UsersRepository;
import dev.scottdickerson.rbacservice.rbacapi.services.ProtectedActionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class APIController {
  private final AccessTierRepository accessTierRepository;
  private final ProtectedActionsRepository protectedActionsRepository;
  private final ProtectedActionService protectedActionService;
  private final UsersRepository usersRepository;

  @GetMapping("/access-tiers")
  public ResponseEntity<List<AccessTier>> getAccessTiers() {
    return ResponseEntity.ok(accessTierRepository.findAll());
  }

  @GetMapping("/access-tiers/{name}")
  public ResponseEntity<AccessTier> getAccessTier(@PathVariable("name") String name) {
    return ResponseEntity.ok(accessTierRepository.findByName(name));
  }

  @GetMapping("/protected-actions")
  public ResponseEntity<List<ProtectedAction>> getProtectedActions() {
    return ResponseEntity.ok(protectedActionsRepository.findAll());
  }

  @GetMapping("/users")
  public ResponseEntity<List<User>> getUsers() {
    List<User> users = usersRepository.findAll();
    users.stream().map(User::getUsername).forEach(log::info);
    return ResponseEntity.ok(usersRepository.findAll());
  }

  @GetMapping(value = "/protected-actions/{name}/{user}")
  public ResponseEntity<ActionAccessCheckResponse>  doesUserHavePermission(@PathVariable String name, @PathVariable String user) {
    return protectedActionService.checkPermission(name, user);
  }
}
