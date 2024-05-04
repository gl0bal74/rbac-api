package dev.scottdickerson.rbacservice.rbacapi.repositories;

import java.util.List;
import java.util.UUID;

import dev.scottdickerson.rbacservice.rbacapi.model.Credential;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialsRepository extends CrudRepository<Credential, UUID> {

  @Override
  @NonNull
  List<Credential> findAll();

  @Override
  void deleteAll();

  Credential findByUserNameAndIntentName(String userName, String intentName);

}
