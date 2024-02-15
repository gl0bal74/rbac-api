package dev.scottdickerson.rbacservice.rbacapi.services;

import dev.scottdickerson.rbacservice.rbacapi.model.AccessTier;
import dev.scottdickerson.rbacservice.rbacapi.model.responses.ActionAccessCheckResponse;
import dev.scottdickerson.rbacservice.rbacapi.repositories.AccessTierRepository;
import dev.scottdickerson.rbacservice.rbacapi.repositories.ProtectedActionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProtectedActionService {
  private final ProtectedActionsRepository protectedActionsRepository;
  private final AccessTierRepository accessTierRepository;
  public ResponseEntity<ActionAccessCheckResponse> checkPermission(String action, String user) {
    log.info("Checking permission for action {} for user {}", action, user);
    AccessTier usersAccessTier = accessTierRepository.findByUsers_Username(user);
    AccessTier protectedActionsAccessTier = accessTierRepository.findByProtectedActions_Name(action);

    log.info("User access tier: {}", usersAccessTier);
    log.info("Protected action access tier: {}", protectedActionsAccessTier);

    ActionAccessCheckResponse response = ActionAccessCheckResponse.builder().user(user).action(action).build();
    if (usersAccessTier == null) {
      log.info("User {} not found", user);
      throw new IllegalArgumentException("User "+user+" not found");
//      response.setError("User "+user+" not found");
//      return ResponseEntity.badRequest().body(response);
    }
    if (protectedActionsAccessTier == null) {
      log.info("Protected action {} not found", action);
      throw new IllegalArgumentException("Protected action "+action +" not found");
//      response.setError("Protected action "+action +" not found");
//      return ResponseEntity.badRequest().body(response);
    }
    response.setHasPermission(usersAccessTier.getHierarchy() >= protectedActionsAccessTier.getHierarchy());
    return ResponseEntity.ok(response);
  }


}
