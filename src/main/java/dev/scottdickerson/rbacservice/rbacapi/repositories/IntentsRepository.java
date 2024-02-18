package dev.scottdickerson.rbacservice.rbacapi.repositories;

import dev.scottdickerson.rbacservice.rbacapi.model.Intent;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface IntentsRepository extends CrudRepository<Intent, UUID> {
  @Override
  @NonNull
  List<Intent> findAll();
}
