package dev.scottdickerson.rbacservice.rbacapi.services;

import dev.scottdickerson.rbacservice.rbacapi.model.AccessTier;
import dev.scottdickerson.rbacservice.rbacapi.model.Intent;
import dev.scottdickerson.rbacservice.rbacapi.model.User;
import dev.scottdickerson.rbacservice.rbacapi.model.requests.ActionAccessCheckRequest;
import dev.scottdickerson.rbacservice.rbacapi.model.responses.ActionAccessCheckResponse;
import dev.scottdickerson.rbacservice.rbacapi.repositories.AccessTierRepository;
import dev.scottdickerson.rbacservice.rbacapi.repositories.IntentsRepository;
import dev.scottdickerson.rbacservice.rbacapi.repositories.UsersRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class IntentsService {
  private final IntentsRepository intentsRepository;
  private final AccessTierRepository accessTierRepository;
  private final UsersRepository usersRepository;

  public ResponseEntity<ActionAccessCheckResponse> checkPermission(
      ActionAccessCheckRequest request) {
    UUID userId = request.userId();
    UUID intentId = request.intentId();
    String password = request.password();
    log.info("Checking permission for intent {} for user {}", intentId, userId);
    Optional<User> userOptional = usersRepository.findById(userId);
    Optional<Intent> intentOptional = intentsRepository.findById(intentId);

    if (userOptional.isEmpty()) {
      log.info("User {} not found", userId);
      throw new IllegalArgumentException("User " + userId + " not found");
    }
    if (intentOptional.isEmpty()) {
      log.info("Protected intent {} not found", intentId);
      throw new IllegalArgumentException("Protected intent " + intentId + " not found");
    }
    User user = userOptional.get();
    Intent intent = intentOptional.get();

    AccessTier usersAccessTier = accessTierRepository.findByUsers(user);
    AccessTier protectedActionsAccessTier = accessTierRepository.findByIntents(intent);

    log.info("User access tier: {}", usersAccessTier);
    log.info("Protected intent access tier: {}", protectedActionsAccessTier);

    ActionAccessCheckResponse response =
        ActionAccessCheckResponse.builder()
            .user(user.getUsername())
            .action(intent.getName())
            .build();
    if (usersAccessTier == null) {
      log.info("User {} not found in access tier", user);
      throw new IllegalArgumentException("User " + user + " not found in access tier");
    }
    if (protectedActionsAccessTier == null) {
      log.info("Protected intent {} not found in intent tier", intent);
      throw new IllegalArgumentException(
          "Protected intent " + intent + " not found in access tier");
    }
    boolean hasPermission =
        usersAccessTier.getHierarchy() >= protectedActionsAccessTier.getHierarchy();
    boolean sudoCheckPassed =
        protectedActionsAccessTier.isAllowSudo()
            && protectedActionsAccessTier.getSudoPassword() != null
            && protectedActionsAccessTier.getSudoPassword().equals(password);

    boolean allowAction = hasPermission || sudoCheckPassed;

    if (allowAction) {
      log.info("User {} has permission to perform action {}", user.getUsername(), intent.getName());
      response.setMessage(
          !hasPermission && sudoCheckPassed
              ? "You have given the correct password"
              : "You have permission to perform this action");
    }

    if (!allowAction) {
      log.info(
          "User {} does not have permission to perform action {}",
          user.getUsername(),
          intent.getName());
      log.info("Did sudo check pass? {}", sudoCheckPassed);
      response.setMessage(
          !sudoCheckPassed
              ? "The access tier '" + protectedActionsAccessTier.getName() + "' requires a password"
              : "You do not have permission to perform this action.");
    }

    response.setHasPermission(allowAction);
    response.setAllowsSudo(protectedActionsAccessTier.isAllowSudo());

    if (!allowAction) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
    return ResponseEntity.ok(response);
  }
}
