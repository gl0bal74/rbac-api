package dev.scottdickerson.rbacservice.rbacapi.services;

import dev.scottdickerson.rbacservice.rbacapi.model.AccessTier;
import dev.scottdickerson.rbacservice.rbacapi.model.User;
import dev.scottdickerson.rbacservice.rbacapi.model.requests.ActionAccessCheckRequest;
import dev.scottdickerson.rbacservice.rbacapi.model.responses.ActionAccessCheckResponse;
import dev.scottdickerson.rbacservice.rbacapi.repositories.AccessTierRepository;
import dev.scottdickerson.rbacservice.rbacapi.repositories.ProtectedActionsRepository;
import dev.scottdickerson.rbacservice.rbacapi.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProtectedActionService {
  private final ProtectedActionsRepository protectedActionsRepository;
  private final AccessTierRepository accessTierRepository;
  private final UsersRepository usersRepository;

  public ResponseEntity<ActionAccessCheckResponse> checkPermission(
      ActionAccessCheckRequest request) {
    String username = request.userName();
    String action = request.protectedActionName();
    String password = request.password();
    log.info("Checking permission for action {} for user {}", action, username);
    User user = usersRepository.findByUsername(username);
    AccessTier usersAccessTier = accessTierRepository.findByUsers_Username(username);
    AccessTier protectedActionsAccessTier =
        accessTierRepository.findByProtectedActions_Name(action);

    log.info("User access tier: {}", usersAccessTier);
    log.info("Protected action access tier: {}", protectedActionsAccessTier);

    ActionAccessCheckResponse response =
        ActionAccessCheckResponse.builder().user(username).action(action).build();
    if (usersAccessTier == null) {
      log.info("User {} not found", username);
      throw new IllegalArgumentException("User " + username + " not found");
    }
    if (protectedActionsAccessTier == null) {
      log.info("Protected action {} not found", action);
      throw new IllegalArgumentException("Protected action " + action + " not found");
    }
    boolean hasPermission =
        usersAccessTier.getHierarchy() >= protectedActionsAccessTier.getHierarchy();
    boolean sudoCheckPassed =
        protectedActionsAccessTier.isAllowSudo()
            && protectedActionsAccessTier.getSudoPassword() != null
            && protectedActionsAccessTier.getSudoPassword().equals(password);

    boolean allowAction = hasPermission || sudoCheckPassed;

    if (allowAction) {
      log.info("User {} has permission to perform action {}", username, action);
      response.setMessage(
          !hasPermission && sudoCheckPassed
              ? "You have given the correct password"
              : "You have permission to perform this action");
    }

    if (!allowAction) {
      log.info("User {} does not have permission to perform action {}", username, action);
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
