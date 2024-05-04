package dev.scottdickerson.rbacservice.rbacapi.repositories;

import dev.scottdickerson.rbacservice.rbacapi.model.Credential;
import dev.scottdickerson.rbacservice.rbacapi.model.Intent;
import dev.scottdickerson.rbacservice.rbacapi.model.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialsRepository extends CrudRepository<Credential, UUID> {

  @Override
  @NonNull
  @Query("SELECT c FROM Credential c JOIN FETCH c.user JOIN FETCH c.intent")
  List<Credential> findAll();

  // can I change this to also write out the User and Intent object as JSON children?

  @Override
  void deleteAll();

  Credential findByUserAndIntent(User user, Intent intentName);
}
