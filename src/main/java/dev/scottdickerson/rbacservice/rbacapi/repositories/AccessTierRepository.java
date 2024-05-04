package dev.scottdickerson.rbacservice.rbacapi.repositories;

import dev.scottdickerson.rbacservice.rbacapi.model.AccessTier;
import dev.scottdickerson.rbacservice.rbacapi.model.Intent;
import dev.scottdickerson.rbacservice.rbacapi.model.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessTierRepository extends CrudRepository<AccessTier, UUID> {

  @Override
  @NonNull
  List<AccessTier> findAll();

  AccessTier findByName(String name);

  AccessTier findByIntents(Intent intent);

  AccessTier findByUsers(User user);

  AccessTier findByIntentsAndUsers(Intent intent, User user);
}
