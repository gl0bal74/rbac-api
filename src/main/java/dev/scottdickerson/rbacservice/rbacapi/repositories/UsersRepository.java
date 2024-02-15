package dev.scottdickerson.rbacservice.rbacapi.repositories;

import dev.scottdickerson.rbacservice.rbacapi.model.AccessTier;
import dev.scottdickerson.rbacservice.rbacapi.model.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<User, UUID> {
  @Override
  @NonNull
  List<User> findAll();

}
