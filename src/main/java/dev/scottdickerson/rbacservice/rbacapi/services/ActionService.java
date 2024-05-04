package dev.scottdickerson.rbacservice.rbacapi.services;

import dev.scottdickerson.rbacservice.rbacapi.model.Credential;
import dev.scottdickerson.rbacservice.rbacapi.repositories.CredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActionService {
  private final CredentialsRepository credentialsRepository;

  public ResponseEntity<String> performAction(String intentName, String username) {
    Credential matchingCredential = credentialsRepository.findByUserNameAndIntentName(username, intentName);
    if (matchingCredential != null) {
      return ResponseEntity.ok("Took action for Credential" + matchingCredential);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No matching Credential found");
    }
  }
}
