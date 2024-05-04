package dev.scottdickerson.rbacservice.rbacapi.services;

import dev.scottdickerson.rbacservice.rbacapi.model.Credential;
import dev.scottdickerson.rbacservice.rbacapi.model.Intent;
import dev.scottdickerson.rbacservice.rbacapi.model.User;
import dev.scottdickerson.rbacservice.rbacapi.repositories.CredentialsRepository;
import dev.scottdickerson.rbacservice.rbacapi.repositories.IntentsRepository;
import dev.scottdickerson.rbacservice.rbacapi.repositories.UsersRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActionService {
  private final CredentialsRepository credentialsRepository;
  private final UsersRepository usersRepository;
  private final IntentsRepository intentsRepository;

  public ResponseEntity<String> performAction(UUID intentId, UUID userId) {
    Optional<User> userOrNull = usersRepository.findById(userId);
    Optional<Intent> intentOrNull = intentsRepository.findById(intentId);
    if (userOrNull.isEmpty() || intentOrNull.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Intent not found");
    }
    // convert optional user to real user object
    User user = userOrNull.get();
    // convert optional intent to real intent object
    Intent intent = intentOrNull.get();
    Credential matchingCredential = credentialsRepository.findByUserAndIntent(user, intent);

    if (matchingCredential != null) {
      return ResponseEntity.ok("Took action for Credential" + matchingCredential);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No matching Credential found");
    }
  }
}
