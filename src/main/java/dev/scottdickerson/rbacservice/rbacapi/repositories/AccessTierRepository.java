package dev.scottdickerson.rbacservice.rbacapi.repositories;

import dev.scottdickerson.rbacservice.rbacapi.model.AccessTier;
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
}
